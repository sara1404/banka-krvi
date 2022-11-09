import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Route, RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComponentsModule } from './components/components.module';
import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { UsersModule } from './views/users/users.module';
import { MaterialModule } from './material/material.module';
import { RegisterAdminModule } from './views/register-admin/register-admin.module';
import { RegisterBloodbankModule } from './views/register-bloodbank/register-bloodbank.module';

import { BloodBankDisplayModule } from './views/blood-banks-display/blood-bank-display.module';
import { UserProfileModule } from './views/user-profile/user-profile.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    BloodBankDisplayModule,
    BrowserAnimationsModule,
    NgbModule,
    ComponentsModule,
    MaterialModule,
    UsersModule,
    UserProfileModule,
    RegisterAdminModule,
    RegisterBloodbankModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
