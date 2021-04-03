import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//pages
import { LayoutComponent } from '../app/core/layout/layout.component';
import { HomeComponent } from '../app/pages/home/home.component';
import { DetailpageComponent } from "../app/pages/detailpage/detailpage.component";
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { MememakerpageComponent } from './pages/mememakerpage/mememakerpage.component'


const routes: Routes = [
  {
    path: '', component: LayoutComponent,
    children: [
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      },
      { path: 'home', component: HomeComponent },
      { path: 'detail/:id', component: DetailpageComponent },
      { path: 'login', component: LoginComponent },
      { path: 'signup', component: SignupComponent },
    ],
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }