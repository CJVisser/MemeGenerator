import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/models/Category';
import { SearchService } from 'src/app/services/search/search.service';
import { environment } from 'src/environments/environment';
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  categories: any
  chosenCategoryId: number

  constructor(private searchService: SearchService, private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.httpClient.get<Category[]>(`${environment.apiUrl}/category/`).subscribe(x => this.categories = x);
  }

  seachText(value){
    this.searchService.searchDone(value);
  }

  onCategoryChange(value){
    this.searchService.categoryChanged(value);
  }
}
