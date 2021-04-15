import { Component, Input, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { MemeService } from 'src/app/services/meme/memeService';

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

  status: boolean = false
  
  constructor(
    protected httpClient: HttpClient,
    protected memeService: MemeService
  ) {
  }

  ngOnInit(): void {
  }

  flagMeme(id): void {
    this.memeService.FlagMeme(id);

    this.status = true

    alert("De meme is gerapporteerd!")
  }

}
