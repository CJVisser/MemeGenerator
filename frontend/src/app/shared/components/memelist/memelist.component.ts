import { Component, OnInit } from '@angular/core';
import { MemeService } from 'src/app/services/meme/memeService';
import { SearchService } from 'src/app/services/search/search.service';
import { Meme } from "../../../../app/models/Meme";
class ResponseClass {
  memeId: number;
  isUpvote: boolean;

  constructor(jsonStr: string) {
    let jsonObj: any = JSON.parse(jsonStr);
    for (let prop in jsonObj) {
        this[prop] = jsonObj[prop];
    }
  }
}

@Component({
  selector: 'memelist',
  templateUrl: './memelist.component.html',
  styleUrls: ['./memelist.component.scss']
})
export class MemelistComponent implements OnInit {
  memes: Meme[];
  allMemes: Meme[];
  searchValue: string = '';
  categoryId: number = 0;

  constructor(private memeService: MemeService, private searchService: SearchService
  ) {
    searchService.updatetSearch.subscribe(x => this.filterMemes(x));
    searchService.updatetCategory.subscribe(x => this.filterMemesCategory(x));
  }

  ngOnInit(): void {
    this.memeService.GetAllMemes().subscribe(memes => {
      this.memes = memes;
      this.allMemes = memes;

      this.memes = this.memes.filter(a => a.memestatus !== "cancelled")

      this.memes.forEach(function(element){
        element.imageSrc = 'data:image/png;base64,' + element.imageblob;
      });
    });
  }

  private filterMemes(value: any) : void {
    this.searchService = value;
    this.memes = this.allMemes.filter(x => x.title.toLowerCase().indexOf(value.toLowerCase()) !== -1 && (this.categoryId === 0 || x.categoryId === this.categoryId));
  }

  private filterMemesCategory(value: any) : void {
    this.categoryId = Number(value);
    this.memes = this.allMemes.filter(x => x.title.toLowerCase().indexOf(this.searchValue.toLowerCase()) !== -1 && x.categoryId === Number(value));
  }
}
