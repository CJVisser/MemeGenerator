import { Component, OnInit } from '@angular/core';
import { Meme } from 'src/app/models/Meme';
import { User } from 'src/app/models/User';
import { TableComponent } from 'src/app/shared/components/table/table.component';
import { AdminService } from 'src/services/admin/adminService';
import { MemeService } from 'src/services/meme/memeService';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  headersUsers: any[] = [
    {text: "Username", value: "username"},
    {text: "Creation Date", value: "createdat"},
    {text: "# Reported Memes", value: "numOfReported"},
    {text: "Points", value: "points"},
    {text: "# of Memes", value: "numOfMemes"},
  ];
  headersMemes: any[] = [
    {text: "Meme Title", value: "title"},
    {text: "Meme Description", value: "description"},
    {text: "Creation Date", value: "createdat"},
    {text: "Username", value: "user.username"},
    {text: "Reported", value: "reported"},
  ]
  userItems: any[] = [
    {
        userName: "Bobbert",
        createDat: "03-12-13",
        numOfReported: 8,
        points: 0,
        numOfMemes: 5,
    },
    {
        userName: "Cornie",
        createDat: "04-12-11",
        numOfReported: 999,
        points: 10,
        numOfMemes: 10,
    },
    {
        userName: "Andhe",
        createDat: "20-01-14",
        numOfReported: 420,
        points: -500,
        numOfMemes: 365,
    },
    {
        userName: "Dove",
        createDat: "12-08-00",
        numOfReported: 0,
        points: 21,
        numOfMemes: 7,
    },
    {
        userName: "Thommert",
        createDat: "01-01-90",
        numOfReported: -10,
        points: 69,
        numOfMemes: 13,
    },
  ];
  userUrl: string = 'iets';
  users: User[] = [];
  memes: any[] = [];

  constructor(
    private adminService: AdminService,
    private memeService: MemeService,
  ) { }

  ngOnInit(): void {
    this.adminService.getUsers()
    .subscribe( data => {
      this.users = data;
      console.log(this.users);
    });
    this.memeService.GetAllMemes()
    .subscribe( data => {
      console.log(data);
      this.memes = data;
      this.getWantedProps();
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
