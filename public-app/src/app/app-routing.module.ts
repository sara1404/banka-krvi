import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DisplayBloodBanksComponent } from './blood-banks-display/blood-banks-display.component';
import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { UsersComponent } from './views/users/users.component';

const routes: Routes = [
  {
  path: 'bloodBanks',
  component: DisplayBloodBanksComponent,
  },
  {path: 'register/admin', component: RegisterAdminComponent},
  {path: 'users', component: UsersComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
