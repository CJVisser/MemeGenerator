import { Component, OnInit } from '@angular/core';
import { Meme } from 'src/app/models/Meme';
import { User } from 'src/app/models/User';
import { TableComponent } from 'src/app/shared/components/table/table.component';
import { AdminService } from 'src/app/services/admin/adminService';
import { MemeService } from 'src/app/services/meme/memeService';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  headersUsers: any[] = [
    {text: "Username", value: "username"},
    {text: "Creation Date", value: "createdat"},
    {text: "Banned", value: "banned"},
    {text: "Points", value: "points"},
    {text: "# of Memes", value: "numOfMemes"},
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
  ) { }

  ngOnInit(): void {
    this.adminService.getUsers()
    .subscribe( data => {
      this.users = data;
      console.log(data);
    });
    this.memeService.GetAllMemes()
    .subscribe( data => {
      console.log(data);
      this.memes = data;
      this.getWantedProps();
    })
    this.userButtonText = 'Ban';
  }

  public banUser(userId: number): void {
    this.adminService.banUser(userId)
    .subscribe( data => {
      console.log(data);
    })
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
