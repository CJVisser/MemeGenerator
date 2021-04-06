import { Component, Input, OnInit } from '@angular/core';
import { MemelistComponent } from '../memelist/memelist.component'

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

  appComponent: MemelistComponent;

  constructor(appComponent: MemelistComponent){
      this.appComponent = appComponent;
  }

  ngOnInit(): void {
    
  }

  sendMessage(voteType){
    this.appComponent.sendMessage(voteType, this.memeId)
  }

}
