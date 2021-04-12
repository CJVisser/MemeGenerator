import { Component, Injector, OnInit } from '@angular/core';
import { User } from '../../../models/User';
import { LoginService } from 'src/app/services/login/loginService';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  user : any

  constructor(private loginService: LoginService) {

    loginService.getLoggedInUser.subscribe(user => this.updateUser(user))
  }

  ngOnInit(): void {
  }

  private updateUser(user: any) : void {
    this.user = user
  }

  private logout(){
    this.loginService.logout()

    
  }
}
