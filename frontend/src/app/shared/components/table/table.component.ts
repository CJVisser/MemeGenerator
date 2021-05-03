import { Component, Input, OnInit } from "@angular/core";

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {

    @Input() items: any[];
    @Input() headers: any[];
    @Input() buttonText: string;
    @Input() actionButton: (Id: number) => void;

    action: (Id: number) => void;

    constructor() { }

    ngOnInit(): void {
        this.activateButton();
    }

    activateButton(){
        const button = document.querySelector('.action-button');
            button.addEventListener("click", function(event){
                console.log(this.actionButton);
            })
    }

    display() {
        console.log(this.actionButton);
    }
}