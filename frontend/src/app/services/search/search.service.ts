import { Injectable } from "@angular/core";
import { EventEmitter, Output } from '@angular/core';

@Injectable({
  providedIn: "root",
})
export class SearchService {
  @Output() updatetSearch: EventEmitter<any> = new EventEmitter();
  @Output() updatetCategory: EventEmitter<any> = new EventEmitter();

  constructor( ) { }

  public searchDone(searchValue){
    this.updatetSearch.emit(searchValue);
  }

  public categoryChanged(value){
    this.updatetCategory.emit(value);
  }
}
