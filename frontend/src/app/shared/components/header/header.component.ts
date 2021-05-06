import { Component, NgModule, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/services/category/category.service';
import { SearchService } from 'src/app/services/search/search.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  categories: any
  chosenCategoryId: number

  constructor(private searchService: SearchService, private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(x => this.categories = x);
  }

  seachText(value){
    this.searchService.searchDone(value);
  }

  onCategoryChange(value){
    this.searchService.categoryChanged(value);
  }
}
