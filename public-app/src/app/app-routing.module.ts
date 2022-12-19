import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
/*
<<<<<<< HEAD
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
import { ExaminationComponent } from './views/users/examination/examination.component';
import {CreateAppointmentComponent} from './views/appointments/create-appointment.component'
=======
>>>>>>> develop*/
import { UserLoginComponent } from './views/user-login/user-login/user-login.component';
import { MainAppComponent } from './components/main-app/main-app.component';

const routes: Routes = [
/*
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
  {path: 'login', component: UserLoginComponent },
  {path: 'confirm/user/:email', component: ConfirmRegistrationComponent},
  {path: 'create-appointment', component: CreateAppointmentComponent},
  {path: 'create-appointment-user', component: CreateAppointmentUserComponent}
  ]
*/
  {
    path: '',
    component: UserLoginComponent,
  },
  {
    path: 'app',
    component: MainAppComponent,
    loadChildren: () =>
      import('./user-routing/user-routing.module').then(
        (x) => x.UserRoutingModule
      ),
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule { }
