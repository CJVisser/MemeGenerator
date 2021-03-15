import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'meme',
  templateUrl: './meme.component.html',
  styleUrls: ['./meme.component.scss']
})
export class MemeComponent implements OnInit {
  @Input() memeId;
  @Input() memeName;
  @Input() memeImageUrl;
  @Input() memeUpvotes;
  @Input() memeDownvotes;
  
  constructor() { }

  ngOnInit(): void {
  }

}
