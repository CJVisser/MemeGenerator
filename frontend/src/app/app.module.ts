import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule  } from '@angular/common/http';
import { CreateComponent } from './create/create.component';
import { FormsModule ,ReactiveFormsModule } from '@angular/forms';
import { CoreComponent } from './core/core.component';
import { SharedComponent } from './shared/shared.component';
import { MemelistComponent } from './shared/components/memelist/memelist.component';
import { MemeComponent } from './shared/components/meme/meme.component';
import { DetailpageComponent } from './pages/detailpage/detailpage.component';
import { LayoutComponent } from './core/layout/layout.component';
import { HomeComponent } from './pages/home/home.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { FooterComponent } from './shared/components/footer/footer.component';
@NgModule({
  declarations: [
    AppComponent,
    CreateComponent,
    CoreComponent,
    SharedComponent,
    MemelistComponent,
    MemeComponent,
    DetailpageComponent,
    LayoutComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule ,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  exports:[
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
