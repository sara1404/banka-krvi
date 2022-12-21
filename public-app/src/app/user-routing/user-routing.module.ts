import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AdminInfoComponent } from "../admin-profile/admin-info/admin-info.component";
import { BloodSuppliesComponent } from "../admin-profile/blood-supplies/blood-supplies.component";
import { BloodbankInfoComponent } from "../admin-profile/bloodbank-info/bloodbank-info.component";
import { ChangePasswordComponent } from "../admin-profile/change-password/change-password.component";
import { FreeAppointmentsComponent } from "../admin-profile/free-appointments/free-appointments.component";
import { OtherAdminsComponent } from "../admin-profile/other-admins/other-admins.component";
import { AdminCalendarComponent } from "../views/admin-calendar/admin-calendar.component";
import { CreateAppointmentUserComponent } from "../views/appointments/create-appointment-user/create-appointment-user.component";
import { CreateAppointmentComponent } from "../views/appointments/create-appointment.component";
import { DisplayBloodBanksComponent } from "../views/blood-banks-display/blood-banks-display.component";
import { DonationSurveyComponent } from "../views/donation-survey/donation-survey.component";
import { RegisterAdminComponent } from "../views/register-admin/register-admin.component";
import { RegisterBloodbankComponent } from "../views/register-bloodbank/register-bloodbank.component";
import { RegisterUserComponent } from "../views/register-user/register-user/register-user.component";
import { UserLoginComponent } from "../views/user-login/user-login/user-login.component";
import { UserProfileComponent } from "../views/user-profile/user-profile.component";
import { ExaminationComponent } from "../views/users/examination/examination.component";
import { UsersComponent } from "../views/users/users.component";

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
  {path: 'examiantion', component:ExaminationComponent},
  {path: 'create-appointment', component: CreateAppointmentComponent},
  {path: 'admin-calendar', component: AdminCalendarComponent},
  {path: 'create-appointment-user', component: CreateAppointmentUserComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
