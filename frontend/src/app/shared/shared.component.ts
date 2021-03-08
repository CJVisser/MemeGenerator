import { NgModule,ModuleWithProviders } from '@angular/core';

//components
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';


@NgModule({
    declarations: [
        HeaderComponent,
        FooterComponent
    ],
    imports: [

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
  