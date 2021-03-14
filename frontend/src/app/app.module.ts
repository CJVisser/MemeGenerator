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
@NgModule({
  declarations: [
    AppComponent,
    MemelistComponent,
    MemeComponent,
    DetailpageComponent,
    LayoutComponent,
    HomeComponent,
    LoginComponent,
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule ,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SharedModule.forRoot()
  ],
  exports:[
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
