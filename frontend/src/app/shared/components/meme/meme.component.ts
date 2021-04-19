import { Component, Input, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { MemeService } from 'src/app/services/meme/memeService';
import { LoginService } from 'src/app/services/login/loginService';
import { User } from 'src/app/models/User';
import { ProfileService } from 'src/app/services/profile/profile.service';
import { Router, ActivatedRoute } from '@angular/router';

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
  @Input() tags;

  status: boolean = false
  user: any = null
  showButtons: boolean = false
  
  constructor(
    private loginService: LoginService, 
    protected httpClient: HttpClient,
    private router: Router,
    protected memeService: MemeService,
  ) {
  }

  ngOnInit(): void {

    this.user = this.loginService.getCurrentUser()

    if(this.user) this.showButtons = true
  }

  flagMeme(id): void {
    this.memeService.FlagMeme(id);

    this.status = true

    alert("De meme is gerapporteerd!")
  }

}
