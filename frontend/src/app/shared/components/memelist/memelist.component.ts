import { Component, OnInit } from '@angular/core';
import { Meme } from "../../../../models/meme";
@Component({
  selector: 'memelist',
  templateUrl: './memelist.component.html',
  styleUrls: ['./memelist.component.scss']
})
export class MemelistComponent implements OnInit {

  memes: Meme[]
  meme1: Meme ={
    id: 1,
    name: "Test 1",
    imageUrl: "/",
    upvotes: 0,
    downvotes: 0, 
  }

  meme2: Meme ={
    id: 2,
    name: "Test 2",
    imageUrl: "/",
    upvotes: 0,
    downvotes: 0, 
  }
  meme3: Meme ={
    id: 3,
    name: "Test 3",
    imageUrl: "/",
    upvotes: 0,
    downvotes: 0, 
  }

  constructor() { }

  ngOnInit(): void {
    this.memes = [
      this.meme1,
      this.meme2,
      this.meme3
    ]
  }

}
