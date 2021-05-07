import { Component, Input, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { MemeService } from 'src/app/services/meme/memeService';
import { LoginService } from 'src/app/services/login/loginService';
import { User } from 'src/app/models/User';
import { ProfileService } from 'src/app/services/profile/profile.service';
import { Router, ActivatedRoute } from '@angular/router';
import { environment } from "../../../../../src/environments/environment"
import { tap } from "rxjs/operators";

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
  @Input() memeUser;
  @Input() tags;
  @Input() category;
  @Input() description;

  status: boolean = false
  user: any = null
  showButtons: boolean = false
  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    }),
  };

  constructor(
    private loginService: LoginService,
    protected httpClient: HttpClient,
    private router: Router,
    protected memeService: MemeService,
  ) {
  }

  ngOnInit(): void {

    this.user = this.loginService.getCurrentUser()

    if (this.user) this.showButtons = true
  }

  flagMeme(id): void {
    this.memeService.FlagMeme(id);
    this.status = true

    alert("You flagged this meme!")
  }

  like() {
    const likeDislike = {
      memeId: this.memeId,
      userId: this.user.id
    }

    this.httpClient.post(`${environment.apiUrl}/like`, likeDislike, this.httpOptions).subscribe()

    alert("You upvoted this meme!")
  }

  dislike() {
    const likeDislike = {
      memeId: this.memeId,
      userId: this.user.id
    }

    this.httpClient.post(`${environment.apiUrl}/dislike`, likeDislike, this.httpOptions).subscribe()

    alert("You downvoted this meme!")
  }
}
