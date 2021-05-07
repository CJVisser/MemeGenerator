import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin/adminService';
import { MemeService } from 'src/app/services/meme/memeService';
import { LoginService } from 'src/app/services/login/loginService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  user : any

  headersUsers: any[] = [
    {text: "Username", value: "username"},
    {text: "Creation Date", value: "createdat"},
    {text: "Banned", value: "banned"},
    {text: "Points", value: "points"},
  ];
  headersMemes: any[] = [
    {text: "Meme Title", value: "title"},
    {text: "Meme Description", value: "description"},
    {text: "Creation Date", value: "createdat"},
    {text: "Username", value: "user.username"},
    {text: "Status", value: "memestatus"},
  ]
  users: User[] = [];
  memes: any[] = [];
  ban: string = 'Ban';
  userButtonText: string;
  memeButtonText: string;

  constructor(
    private adminService: AdminService,
    private memeService: MemeService,
    private loginService: LoginService, 
    private router: Router
  ) { }

  ngOnInit(): void {

    this.user = this.loginService.getCurrentUser()

    if (!this.user) this.router.navigate(["/login"]);

    if(this.user.role !== "Admin"){
      alert("You are not allowed to see this page.")
    }

    this.adminService.getUsers()
    .subscribe( data => {
      this.users = data;
    });
    this.memeService.GetAllMemes()
    .subscribe( data => {
      this.memes = data;
      this.getWantedProps();
    })
    this.userButtonText = 'Ban';
    this.memeButtonText = 'Cancel';
  }

  public banUser(userId: number): void {
    this.adminService.banUser(userId)
    .subscribe( () => {})
  }

  public cancelMeme(memeId: number): void {
    this.adminService.cancelMeme(memeId)
    .subscribe( () => {})
  }

  getWantedProps() {
    this.headersMemes.forEach(header => {
      if (header.value.includes('.')) {
        var headerParts = header.value.split('.');
        this.memes.forEach(item => {
            var specificItem = item[headerParts[0]];
            item[header.value] = specificItem[headerParts[1]];
        });
      }
    });
  }

}
