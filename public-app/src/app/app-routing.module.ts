import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { RegisterBloodbankComponent } from './views/register-bloodbank/register-bloodbank.component';
import { UsersComponent } from './views/users/users.component';
import { DisplayBloodBanksComponent } from './blood-banks-display/blood-banks-display.component';

const routes: Routes = [
  {
    path: 'bloodBanks',
    component: DisplayBloodBanksComponent,
  },
  {
    path: 'register/admin',
    component: RegisterAdminComponent
  },
  {
    path: 'register/bloodbank',
    component: RegisterBloodbankComponent
  },
  {
    path: 'users',
    component: UsersComponent
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
