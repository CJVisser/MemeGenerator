import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//pages
import { LayoutComponent } from '../app/core/layout/layout.component';
import { HomeComponent } from '../app/pages/home/home.component';
import { DetailpageComponent } from "../app/pages/detailpage/detailpage.component";
import { LoginComponent } from './pages/login/login.component';
import { AdminComponent } from './pages/admin/admin.component';
import { SignupComponent } from './pages/signup/signup.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { MememakerpageComponent } from './pages/mememakerpage/mememakerpage.component'


const routes: Routes = [
  { path: '', component: LayoutComponent,
  children: [
    {
      path: '',
      redirectTo: 'home',
      pathMatch: 'full'
    },
    {
      path: 'home',
      //TODO: Create childrens if needed 
      // loadChildren: () =>
      //   import('./pages/home/home.component').then(
      //     (m) => m.HomeComponent
      //   )
      component : HomeComponent
    },
    { path: 'detail/:id', component: DetailpageComponent},
    // { path: 'create', component: CreatepageComponent},
    { path: 'login', component: LoginComponent },
    { path: 'admin', component: AdminComponent },
    { path: 'signup', component: SignupComponent} ,
    { path: 'profile/:id', component: ProfileComponent},
    { path: 'mememaker', component: MememakerpageComponent},
    ],

   
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
