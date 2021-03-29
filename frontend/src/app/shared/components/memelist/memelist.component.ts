import { Component, OnInit } from '@angular/core';
import { MemeService } from 'src/services/meme/memeService';
import { Meme } from "../../../../app/models/Meme";

@Component({
  selector: 'memelist',
  templateUrl: './memelist.component.html',
  styleUrls: ['./memelist.component.scss']
})

export class MemelistComponent implements OnInit {

  memes: Meme[]

  constructor(private memeService: MemeService) { }

  ngOnInit(): void {
    this.memeService.GetAllMemes().subscribe(memes => {
      this.memes = memes;

      this.memes.forEach(function(element){
        element.imageSrc = 'data:image/png;base64,' + element.imageblob;
      });
    });
  }
}
