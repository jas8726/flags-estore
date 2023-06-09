import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FlagDetailComponent } from './flag-detail/flag-detail.component';
import { FlagsComponent } from './flags/flags.component';
import { FlagSearchComponent } from './flag-search/flag-search.component';
import { MessagesComponent } from './messages/messages.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CustomflagComponent } from './customflag/customflag.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
    
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    FlagsComponent,
    FlagDetailComponent,
    MessagesComponent,
    FlagSearchComponent,
    LoginComponent,
    RegisterComponent,
    ShoppingCartComponent,
    CustomflagComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }