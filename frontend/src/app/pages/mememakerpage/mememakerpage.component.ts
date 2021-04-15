import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import html2canvas from 'html2canvas';
import { Observable, Observer } from 'rxjs';
import { MemeService } from "../../services/meme/memeService";
import { Meme } from "../../models/Meme"
import { Category } from 'src/app/models/Category';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-mememakerpage',
  templateUrl: './mememakerpage.component.html',
  styleUrls: ['./mememakerpage.component.scss']
})
export class MememakerpageComponent implements OnInit {

  constructor(private memeService: MemeService, private httpClient: HttpClient) { }

  @ViewChild('screen') screen: ElementRef;
  @ViewChild('canvas') canvas: ElementRef;
  @ViewChild('downloadLink') downloadLink: ElementRef;

  @ViewChild('image') image: ElementRef;

  topText: string = "This is top text";
  bottomText: string = "This is bottom text";
  imageName: string = "Magikarp Logo";
  colorName: string = "black";

  title: string;
  description: string;

  base64TrimmedURL: string;
  base64DefaultURL: string;
  generatedImage: string;

  categories: any
  chosenCategoryId: number

  /*Note: In the event that new memes are to be added,
  the meme names and corresponding file-names need to be in the same order in both arrays.*/

  //stores names of all the memes
  imageNameList: string[] = ["Magikarp Logo", "Crying Pepe", "Denerys"];
  //stores file-names of all memes
  imageFileNameList: string[] = ["magikarp_logo.png", "crying_pepe.jpg", "denerys.png"];

  changeImage() {
    var valueOfSearch: string = "";
    for (var i = 0; i < this.imageNameList.length; i++) {
      if (this.imageNameList[i] === this.imageName) {
        valueOfSearch = this.imageFileNameList[i];
      }
    }
    var whole: string = "assets/" + valueOfSearch;
    return whole;
  }

  ngOnInit(): void {
    this.getCategories();
  }

  downloadImage() {

    if (!this.chosenCategoryId) {

      alert('Kies een categorie voor de meme')

      return
    }

    html2canvas(this.screen.nativeElement).then(canvas => {
      this.canvas.nativeElement.src = canvas.toDataURL();
      this.getImage(canvas.toDataURL('image/png'));
    });
  }

  /* Method called from the UI */
  getImage(imageUrl: string) {
    this.getBase64ImageFromURL(imageUrl).subscribe((base64Data: string) => {
      this.base64TrimmedURL = base64Data;
      this.createBlobImageFileAndShow();
    });
  }

  /* Method to fetch image from Url */
  getBase64ImageFromURL(url: string): Observable<string> {
    return Observable.create((observer: Observer<string>) => {
      // create an image object
      let img = new Image();
      img.crossOrigin = "Anonymous";
      img.src = url;
      if (!img.complete) {
        // This will call another method that will create image from url
        img.onload = () => {
          observer.next(this.getBase64Image(img));
          observer.complete();
        };
        img.onerror = err => {
          observer.error(err);
        };
      } else {
        observer.next(this.getBase64Image(img));
        observer.complete();
      }
    });
  }

  /* Method to create base64Data Url from fetched image */
  getBase64Image(img: HTMLImageElement): string {
    // We create a HTML canvas object that will create a 2d image
    var canvas: HTMLCanvasElement = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;
    let ctx: CanvasRenderingContext2D = canvas.getContext("2d");
    // This will draw image
    ctx.drawImage(img, 0, 0);
    // Convert the drawn image to Data URL
    let dataURL: string = canvas.toDataURL("image/png");
    this.base64DefaultURL = dataURL;
    return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
  }

  createBlobImageFileAndShow(): void {
    this.dataURItoBlob(this.base64TrimmedURL).subscribe((blob: Blob) => {
      var meme: Meme = {
        title: this.title,
        description: this.description,
        imageblob: blob,
        categoryId: this.chosenCategoryId,
        userId: 180
      };

      this.memeService.CreateMeme(meme).subscribe((res: HttpResponse<any>) => {
        console.log(res.body);

        alert('Je meme is aangemaakt!')
      });
    });
  }

  /* Method to convert Base64Data Url as Image Blob */
  dataURItoBlob(dataURI: string): Observable<Blob> {
    return Observable.create((observer: Observer<Blob>) => {
      const byteString: string = window.atob(dataURI);
      const arrayBuffer: ArrayBuffer = new ArrayBuffer(byteString.length);
      const int8Array: Uint8Array = new Uint8Array(arrayBuffer);
      for (let i = 0; i < byteString.length; i++) {
        int8Array[i] = byteString.charCodeAt(i);
      }
      const blob = new Blob([int8Array], { type: "image/jpeg" });
      observer.next(blob);
      observer.complete();
    });
  }

  /**Method to Generate a Name for the Image */
  generateName(): string {
    const date: number = new Date().valueOf();
    let text: string = "";
    const possibleText: string =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (let i = 0; i < 5; i++) {
      text += possibleText.charAt(
        Math.floor(Math.random() * possibleText.length)
      );
    }
    // Replace extension according to your media type like this
    return date + "." + text + ".jpeg";
  }

  onFileChanged(event) {
    const files = event.target.files;
    if (files.length === 0)
      return;

    const mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      //this.message = "Only images are supported."; possible error message
      return;
    }

    const reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.image.nativeElement.src = reader.result;
    }
  }

  getCategories() {
    this.httpClient.get<Category[]>(`${environment.apiUrl}/category/`).subscribe(data => this.categories = data)
  }
}
