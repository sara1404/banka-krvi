import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AdminInfoComponent } from "../admin-profile/admin-info/admin-info.component";
import { BloodSuppliesComponent } from "../admin-profile/blood-supplies/blood-supplies.component";
import { BloodbankInfoComponent } from "../admin-profile/bloodbank-info/bloodbank-info.component";
import { ChangePasswordComponent } from "../admin-profile/change-password/change-password.component";
import { FreeAppointmentsComponent } from "../admin-profile/free-appointments/free-appointments.component";
import { OtherAdminsComponent } from "../admin-profile/other-admins/other-admins.component";
import { AuthGuard } from "../services/auth.guard";
import { AdminCalendarComponent } from "../views/admin-calendar/admin-calendar.component";
import { CreateAppointmentUserComponent } from "../views/appointments/create-appointment-user/create-appointment-user.component";
import { CreateAppointmentComponent } from "../views/appointments/create-appointment.component";
import { DisplayBloodBanksComponent } from "../views/blood-banks-display/blood-banks-display.component";
import { DonationSurveyComponent } from "../views/donation-survey/donation-survey.component";
import { PersonalAppointmentsComponent } from "../views/personal-appointments/personal-appointments.component";
import { PredefinedAppointmentsComponent } from "../views/predefined-appointments/predefined-appointments.component";
import { RegisterAdminComponent } from "../views/register-admin/register-admin.component";
import { RegisterBloodbankComponent } from "../views/register-bloodbank/register-bloodbank.component";
import { ConfirmRegistrationComponent } from "../views/register-user/confirm-registration/confirm-registration.component";
import { RegisterUserComponent } from "../views/register-user/register-user/register-user.component";
import { UserLoginComponent } from "../views/user-login/user-login/user-login.component";
import { UserProfileComponent } from "../views/user-profile/user-profile.component";
import { ExaminationComponent } from "../views/users/examination/examination.component";
import { UsersComponent } from "../views/users/users.component";
import { Role } from "../model/Role";
import { AuthService } from "../services/auth.service";

const routes: Routes = [
  {path: 'admin/profile', component:AdminInfoComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'bloodbank/info', component:BloodbankInfoComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'register/admin', component: RegisterAdminComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminSystem]}},
  {path: 'register/bloodbank', component: RegisterBloodbankComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminSystem]}},
  {path: 'register/user', component: RegisterUserComponent},
  {path: 'users', component: UsersComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'bloodBanks', component: DisplayBloodBanksComponent},
  {path: 'userProfile', component: UserProfileComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter, Role.AdminSystem, Role.Registered]}},
  {path: 'donation-survey', component: DonationSurveyComponent, canActivate: [AuthGuard], data:{roles: [Role.Registered]}},
  {path: 'other/admins', component:OtherAdminsComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'free/appointments', component:FreeAppointmentsComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'bloodsupplies', component:BloodSuppliesComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'changePassword', component: ChangePasswordComponent}, //, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter, Role.AdminSystem, Role.Registered]}
  {path: 'examiantion', component:ExaminationComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'create-appointment', component: CreateAppointmentComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'admin-calendar', component: AdminCalendarComponent, canActivate: [AuthGuard], data:{roles: [Role.AdminCenter]}},
  {path: 'confirm/user/:email', component: ConfirmRegistrationComponent, canActivate: [AuthGuard], data:{roles: [Role.Unautentificated]}},
  {path: 'create-appointment-user', component: CreateAppointmentUserComponent, canActivate: [AuthGuard], data:{roles: [Role.Registered]}},
  {path: 'appointments/predefined', component: PredefinedAppointmentsComponent, canActivate: [AuthGuard], data:{roles: [Role.Registered]}},
  {path: 'appointments/personal', component: PersonalAppointmentsComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {}
