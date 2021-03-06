import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule  } from '@angular/common/http';
import { FormsModule ,ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from './shared/shared.component';
import { MemelistComponent } from './shared/components/memelist/memelist.component';
import { MemeComponent } from './shared/components/meme/meme.component';
import { DetailpageComponent } from './pages/detailpage/detailpage.component';
import { LayoutComponent } from './core/layout/layout.component';
import { HomeComponent } from './pages/home/home.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { LoginComponent } from './pages/login/login.component';
import { AdminComponent } from './pages/admin/admin.component';
import { SignupComponent } from './pages/signup/signup.component';
import { MenuComponent } from './shared/components/menu/menu.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { PasswordResetComponent } from './pages/password-reset/password-reset.component';
import { CreateComponent } from './pages/create/create.component';
import { AngularDraggableModule } from 'angular2-draggable';

@NgModule({
  declarations: [
    AppComponent,
    MemelistComponent,
    MemeComponent,
    DetailpageComponent,
    LayoutComponent,
    HomeComponent,
    LoginComponent,
    AdminComponent,
    SignupComponent,
    MenuComponent,
    ProfileComponent,
    PasswordResetComponent,
    CreateComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule ,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SharedModule.forRoot(),
    AngularDraggableModule
  ],
  exports:[
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
