import { HttpResponse } from '@angular/common/http';
import { Component, Injector, ElementRef, OnInit, ViewChild } from '@angular/core';
import html2canvas from 'html2canvas';
import { Observable, Observer } from 'rxjs';
import { MemeService } from "../../services/meme/memeService";
import { Meme } from "../../models/Meme"
import { Category } from 'src/app/models/Category';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { LoginService } from 'src/app/services/login/loginService';
import { User } from 'src/app/models/User';
import { Tag } from 'src/app/models/Tag';
import { ProfileService } from 'src/app/services/profile/profile.service';
import { Router, ActivatedRoute } from '@angular/router';
import { tap } from "rxjs/operators";
import { pipe } from 'rxjs';
import { HtmlTagDefinition } from '@angular/compiler';
import { MemeImage } from 'src/app/models/MemeImage';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

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

  canvas;
  context;

  constructor() {
  }

  ngOnInit(): void {
    this.setImages();
    this.canvas = document.getElementById("canvas");
    this.context = this.canvas.getContext("2d");
  }

  setImages(): void {
    // Fill option with values
    this.images = [
      {
        name: "Magikarp",
        url: "https://img.pokemondb.net/artwork/large/magikarp.jpg"
      },
      {
        name: "Surprized Pikachu",
        url: "https://en.meming.world/images/en/thumb/6/6e/Surprised_Pikachu.jpg/300px-Surprised_Pikachu.jpg"
      },
      {
        name: "SpongeBob 1",
        url: "https://i.pinimg.com/736x/25/2a/04/252a045199e33164a8b7577fc001851a.jpg"
      },
      {
        name: "SpongeBob 2",
        url: "https://i.imgflip.com/29r48m.jpg"
      }
    ]
  }

  addText(x: number, y: number): void {

    const textRectangle = document.getElementById("textPosition").getBoundingClientRect();

    this.context.font = "12px Arial";
    this.context.fillText(this.text, x, y);
    this.text = "";
  }

  setChosenImage(url: string): void {

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

  onMoveEnd(event){
    const canvasRectangle = document.getElementById("canvas").getBoundingClientRect();
    const textRectangle = document.getElementById("textPosition").getBoundingClientRect();

    this.addText(event.x, event.y)
  }
}
