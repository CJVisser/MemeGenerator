import { Component, OnInit } from "@angular/core";
import { User } from "../../models/User";
import { ProfileService } from "../../services/profile/profile.service";
import { LoginService } from 'src/app/services/login/loginService';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: "ngx-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.scss"],
})
export class ProfileComponent implements OnInit {

  updatedProfile: boolean = false
  password: string = ""
  user: User = {username: '', email: '', password: ''};
  id: any;

  constructor(private profileService: ProfileService,
    private route: ActivatedRoute,
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
