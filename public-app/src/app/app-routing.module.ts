import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminProfileModule } from './admin-profile/admin-profile.module';
import { AdminProfileComponent } from './admin-profile/admin-profile/admin-profile.component';


import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { RegisterBloodbankComponent } from './views/register-bloodbank/register-bloodbank.component';
import { UsersComponent } from './views/users/users.component';
import { DisplayBloodBanksComponent } from './views/blood-banks-display/blood-banks-display.component';
import { UserProfileComponent } from './views/user-profile/user-profile.component';
import { AdminInfoComponent } from './admin-profile/admin-info/admin-info.component';
import { BloodbankInfoComponent } from './admin-profile/bloodbank-info/bloodbank-info.component';
import { OtherAdminsComponent } from './admin-profile/other-admins/other-admins.component';
import { FreeAppointmentsComponent } from './admin-profile/free-appointments/free-appointments.component';
import { BloodSuppliesComponent } from './admin-profile/blood-supplies/blood-supplies.component';
import { ChangePasswordComponent } from './admin-profile/change-password/change-password.component';
import { RegisterUserComponent } from './views/register-user/register-user/register-user.component';
import { DonationSurveyComponent } from './views/donation-survey/donation-survey.component';
import {CreateAppointmentComponent} from './views/appointments/create-appointment.component'
import { AdminCalendarComponent } from './views/admin-calendar/admin-calendar.component';


const routes: Routes = [
  {path: 'admin/profile', component:AdminInfoComponent},
  {path: 'bloodbank/info', component:BloodbankInfoComponent},
  {path: 'register/admin', component: RegisterAdminComponent},
  {path: 'register/bloodbank', component: RegisterBloodbankComponent},
  {path: 'register/user', component: RegisterUserComponent},
  {path: 'users', component: UsersComponent},
  {path: 'bloodBanks', component: DisplayBloodBanksComponent},
  {path: 'userProfile', component: UserProfileComponent},
  {path: 'donation-survey', component: DonationSurveyComponent},
  {path: 'other/admins', component:OtherAdminsComponent},
  {path: 'free/appointments', component:FreeAppointmentsComponent},
  {path: 'bloodsupplies', component:BloodSuppliesComponent},
  {path: 'changePassword', component: ChangePasswordComponent},
  {path: 'createAppointment', component: CreateAppointmentComponent},
  {path: 'admin-calendar', component: AdminCalendarComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
