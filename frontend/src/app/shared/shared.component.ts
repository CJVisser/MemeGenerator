import { NgModule,ModuleWithProviders } from '@angular/core';

//components
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from "@angular/common";


@NgModule({
    declarations: [
        HeaderComponent,
        FooterComponent
    ],
    imports: [
        FormsModule,
        BrowserModule,
        CommonModule
    ],
    exports: [
        HeaderComponent,
        FooterComponent
    ],
    providers: [],

  })
  export class SharedModule { 
      public static forRoot(): ModuleWithProviders<SharedModule>{
          return {
              ngModule: SharedModule};
          }
      }
  