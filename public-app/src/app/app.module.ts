import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Route, RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminProfileModule } from './admin-profile/admin-profile.module';
import {MatFormFieldModule} from '@angular/material/form-field';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComponentsModule } from './components/components.module';
import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { UsersModule } from './views/users/users.module';
import { MaterialModule } from './material/material.module';
import { BloodSuppliesComponent } from './admin-profile/blood-supplies/blood-supplies.component';
import { RegisterBloodbankComponent } from './views/register-bloodbank/register-bloodbank.component';


import { RegisterAdminModule } from './views/register-admin/register-admin.module';
import { RegisterBloodbankModule } from './views/register-bloodbank/register-bloodbank.module';
import { BloodBankDisplayModule } from './views/blood-banks-display/blood-bank-display.module';
import { UserProfileModule } from './views/user-profile/user-profile.module';

import {ToastrModule} from "ngx-toastr"

@NgModule({
  declarations: [
    AppComponent,
    BloodSuppliesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    BloodBankDisplayModule,
    BrowserAnimationsModule,
    AdminProfileModule,
    MatFormFieldModule,
    NgbModule,
    ComponentsModule,
    MaterialModule,
    UsersModule,
    UserProfileModule,
    RegisterAdminModule,
    RegisterBloodbankModule,
    ToastrModule.forRoot({
      positionClass: "toast-bottom-right"
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
