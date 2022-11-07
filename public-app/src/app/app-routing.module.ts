import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminProfileModule } from './admin-profile/admin-profile.module';
import { AdminProfileComponent } from './admin-profile/admin-profile/admin-profile.component';


import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { UsersComponent } from './views/users/users.component';

const routes: Routes = [
  {path: 'admin/profile', component:AdminProfileComponent},
  {path: 'register/admin', component: RegisterAdminComponent},
  {path: 'users', component: UsersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
