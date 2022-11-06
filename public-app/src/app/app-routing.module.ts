import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { UsersComponent } from './views/users/users.component';
import { DisplayBloodBanksComponent } from './blood-banks-display/blood-banks-display.component';

const routes: Routes = [
  {path: 'register/admin', component: RegisterAdminComponent},
  {path: 'users', component: UsersComponent},
  {path: 'bloodBanks', component: DisplayBloodBanksComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
