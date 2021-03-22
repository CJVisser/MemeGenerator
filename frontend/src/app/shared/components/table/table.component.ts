import { Component, Input, OnInit } from "@angular/core";

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {

    @Input() headers: any[];
    @Input() dataUrl: string; // Get raw data from backend
    items: any[];

    constructor() { }

    ngOnInit(): void {
        this.items = this.getData();
    }
    
    public getData(){
    // Get data from backend, redesign to desired data format
    return [
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
    ]
    }
}