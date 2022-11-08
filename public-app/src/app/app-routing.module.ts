import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminProfileModule } from './admin-profile/admin-profile.module';
import { AdminProfileComponent } from './admin-profile/admin-profile/admin-profile.component';


import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { RegisterBloodbankComponent } from './views/register-bloodbank/register-bloodbank.component';
import { UsersComponent } from './views/users/users.component';
import { DisplayBloodBanksComponent } from './views/blood-banks-display/blood-banks-display.component';
import { UserProfileComponent } from './views/user-profile/user-profile.component';

const routes: Routes = [
  {path: 'admin/profile', component:AdminProfileComponent},
  {path: 'register/admin', component: RegisterAdminComponent},
  {path: 'register/bloodbank', component: RegisterBloodbankComponent},
  {path: 'users', component: UsersComponent},
  {path: 'bloodBanks', component: DisplayBloodBanksComponent},
  {path: 'userProfile', component: UserProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
