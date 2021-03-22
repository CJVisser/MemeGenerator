import { Component, OnInit } from '@angular/core';
import { TableComponent } from 'src/app/shared/components/table/table.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  headersUsers: any[] = [
    {text: "Username", value: "userName"},
    {text: "Creation Date", value: "createDat"},
    {text: "# Reported Memes", value: "numOfReported"},
    {text: "Points", value: "points"},
    {text: "# of Memes", value: "numOfMemes"},
  ];
  userUrl: string = 'iets';

  constructor() { }

  ngOnInit(): void {
  }

}
