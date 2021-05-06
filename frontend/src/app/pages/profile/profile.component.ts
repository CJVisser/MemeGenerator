import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { User } from "../../models/User";
import { ProfileService } from "../../services/profile/profile.service";
import { LoginService } from 'src/app/services/login/loginService';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

let self: any;

@Component({
  selector: "ngx-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.scss"],
})
export class ProfileComponent implements OnInit {

  updatedProfile: Boolean = false
  password: string = ""
  user: User;
  id: any;

  constructor(private profileService: ProfileService,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService
  ) {
    loginService.getLoggedInUser.subscribe(user => this.getUser(user))
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.profileService.getUserInfo(
      <Number>this.id
    ).subscribe(user => {  
      this.user = user 
    });
  }

  private getUser(user: any) : void {
    this.user = user
  }

  updateUser(): void {
    const e: Event = window.event;
    e.preventDefault();

    this.user.password = this.password;

    this.profileService.updateUserInfo(this.user).subscribe()

    this.updatedProfile = true
  }
}
