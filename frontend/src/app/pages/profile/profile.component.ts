import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { User } from "../../models/User";
// import { AuthService } from "app/services/auth/auth.service";
import { ProfileService } from "../../services/profile/profile.service";
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

  updatedProfile : Boolean = false

  constructor(private profileService: ProfileService, 
    private route: ActivatedRoute
    // private authService: AuthService
    ) {}

  user: User;
  id: any;
  ngOnInit(): void {
    self = this;
    this.id = this.route.snapshot.paramMap.get('id');
    this.profileService.getUserInfo(
          <Number>this.id
          // this.authService.getCurrentUser().id
          ).subscribe(user => {this.user = user});
      
    
  }

  updateUser(): void {
    const e: Event = window.event;
    e.preventDefault();

    self.user.email = (<HTMLInputElement>(
      document.getElementById("email")
    )).value;
    self.user.password = (<HTMLInputElement>(
      document.getElementById("password")
    )).value;

    this.profileService.updateUserInfo(this.user).subscribe()

    this.updatedProfile = true
  }
}
