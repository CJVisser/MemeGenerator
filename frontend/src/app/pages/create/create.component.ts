import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MemeService } from "../../services/meme/memeService";
import { Meme } from "../../models/Meme"
import { Category } from 'src/app/models/Category';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { LoginService } from 'src/app/services/login/loginService';
import { Tag } from 'src/app/models/Tag';
import { Router } from '@angular/router';
import { MemeImage } from 'src/app/models/MemeImage';

@Component({
  selector: 'app-mememakerpage',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {

  text: string = ""
  images: MemeImage[] = []
  chosenImage: MemeImage = {
    name: "",
    url: ""
  }

  title: string = ""
  description: string = ""
  tags: Tag[] = []
  chosenTags: Tag[] = []
  tag: Tag = null
  canvas;
  context;
  categories: any
  chosenCategoryId: number
  user: any = null
  customTag: string = ""

  constructor(private loginService: LoginService,
    private memeService: MemeService, private httpClient: HttpClient,
    private router: Router) {
  }

  ngOnInit(): void {

    this.user = this.loginService.getCurrentUser()

    if (!this.user) this.router.navigate(["/login"]);

    this.getCategories()
    this.getTags()

    this.setImages();
    this.canvas = document.getElementById("canvas");
    this.context = this.canvas.getContext("2d");
  }

  setImages(): void {
    // Fill option with values
    this.images = [
      {
        name: "Magikarp",
        url: "assets/magikarp.jpg"
      },
      {
        name: "Surprised Pikachu",
        url: "assets/pikachu.jpg"
      },
      {
        name: "SpongeBob 1",
        url: "assets/spongebob.jpg"
      },
      {
        name: "SpongeBob 2",
        url: "assets/spongebob2.jpg"
      }
    ]
  }

  addText(x: number, y: number): void {

    const textRectangle = document.getElementById("textPosition").getBoundingClientRect();

    this.context.font = "12px Arial";
    this.context.fillText(this.text, x, y);
    this.text = "";
  }

  setChosenImage(url: any): void {

    this.chosenImage.url = url

    const image = new Image();
    image.src = url;
    image.onload = () => {
      const width = document.getElementById("chooseTemplate").offsetWidth;
      const height = document.getElementById("chooseTemplate").offsetHeight;

      this.context.canvas.width = width;
      this.context.canvas.height = height;
      this.context.drawImage(document.getElementById("meme-image"), 10, 10, width, height);
    }
  }

  onMoveEnd(event) {
    const canvasRectangle = document.getElementById("canvas").getBoundingClientRect();
    const textRectangle = document.getElementById("textPosition").getBoundingClientRect();

    this.addText(event.x, event.y)
  }

  getCategories() {
    this.httpClient.get<Category[]>(`${environment.apiUrl}/category/`).subscribe(data => this.categories = data)
  }

  getTags() {
    this.httpClient.get<Tag[]>(`${environment.apiUrl}/tag/`).subscribe(data => this.tags = data)
  }

  addTag(tagId) {
    const tag = this.tags.find(a => a.id == tagId)

    this.chosenTags.push(tag)
  }

  createMeme() {

    if (this.title == "" || !this.chosenCategoryId) {
      alert("The title or category is empty")

      return
    }

    this.canvas.toBlob((blob) => {
      const meme: Meme = {
        title: this.title,
        description: this.description,
        imageblob: blob,
        categoryId: this.chosenCategoryId,
        userId: this.user.id,
        tags: this.chosenTags
      };

      this.memeService.CreateMeme(meme).subscribe((res: HttpResponse<any>) => {

        if (res.body.Success) {
          alert("Your meme has been created!")
        } else {
          alert(res.body.Message)
        }
      })
    })
  }

  onSelectFile(event) { // called each time file input changes
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();

      reader.readAsDataURL(event.target.files[0])

      reader.onload = (event) => {
        const url = event.target.result;
        this.setChosenImage(url)
      }
    }
  }

  addCustomTag() {
    const tag : Tag = {
      id: 0,
      title: this.customTag
    }

    this.chosenTags.push(tag)

    this.customTag = ""
  }
}
