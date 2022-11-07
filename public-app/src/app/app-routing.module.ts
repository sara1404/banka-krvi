import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { UsersComponent } from './views/users/users.component';
import { DisplayBloodBanksComponent } from './views/blood-banks-display/blood-banks-display.component';
import { UserProfileComponent } from './views/user-profile/user-profile.component';

const routes: Routes = [
  {path: 'register/admin', component: RegisterAdminComponent},
  {path: 'users', component: UsersComponent},
  {path: 'bloodBanks', component: DisplayBloodBanksComponent},
  {path: 'userProfile', component: UserProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
