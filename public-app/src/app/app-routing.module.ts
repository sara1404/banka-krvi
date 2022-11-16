import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { RegisterBloodbankComponent } from './views/register-bloodbank/register-bloodbank.component';
import { UsersComponent } from './views/users/users.component';
import { DisplayBloodBanksComponent } from './views/blood-banks-display/blood-banks-display.component';
import { UserProfileComponent } from './views/user-profile/user-profile.component';
import { RegisterUserComponent } from './views/register-user/register-user/register-user.component';
import { DonationSurveyComponent } from './views/donation-survey/donation-survey.component';

const routes: Routes = [
  {path: 'register/admin', component: RegisterAdminComponent},
  {path: 'register/bloodbank', component: RegisterBloodbankComponent},
  {path: 'register/user', component: RegisterUserComponent},
  {path: 'users', component: UsersComponent},
  {path: 'bloodBanks', component: DisplayBloodBanksComponent},
  {path: 'userProfile', component: UserProfileComponent},
  {path: 'donation-survey', component: DonationSurveyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
