import { Component, OnInit } from '@angular/core';
import { MemeService } from 'src/services/meme/memeService';
import { Meme } from "../../../../app/models/Meme";
import { WebSocketAPI } from './WebSocketAPI';

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
  webSocketAPI: WebSocketAPI;

  constructor(private memeService: MemeService) { }

  ngOnInit(): void {
    this.memeService.GetAllMemes().subscribe(memes => {
      this.memes = memes;

      this.memes.forEach(function(element){
        element.imageSrc = 'data:image/png;base64,' + element.imageblob;
      });
    });

    this.webSocketAPI = new WebSocketAPI(this);
    this.connect();
  }

  ngOnDestroy(){
    this.disconnect();
  }

  sendMessage(voteType, memeId){
    if(true){ // check if user logged in
      let response;
      if(voteType === "u"){
        response = {memeId: memeId, isUpvote: true, userId: 10} // Use correct userId
      }else{
        response = {memeId: memeId, isUpvote: false, userId: 10}
      }

      this.webSocketAPI._send(response);
    }
  }

  handleMessage(message){
    let fObj: ResponseClass = new ResponseClass(message);
    var item2 = this.memes.filter(function(item) {
      return item.id === fObj.memeId;
    })[0];

    if(fObj.isUpvote){
      item2.likes++;
    }else{
      item2.dislikes++;
    }
  }

  connect(){
    this.webSocketAPI._connect();
  }

  disconnect(){
    this.webSocketAPI._disconnect();
  }
}
