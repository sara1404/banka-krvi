import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserLoginComponent } from './views/user-login/user-login/user-login.component';
import { MainAppComponent } from './components/main-app/main-app.component';


// const routes: Routes = [
//   {path: '', component: HomepageComponent},
//   {path: 'admin/profile', component:AdminInfoComponent},
//   {path: 'bloodbank/info', component:BloodbankInfoComponent},
//   {path: 'register/admin', component: RegisterAdminComponent},
//   {path: 'register/bloodbank', component: RegisterBloodbankComponent},
//   {path: 'register/user', component: RegisterUserComponent},
//   {path: 'users', component: UsersComponent},
//   {path: 'bloodBanks', component: DisplayBloodBanksComponent},
//   {path: 'userProfile', component: UserProfileComponent},
//   {path: 'donation-survey', component: DonationSurveyComponent},
//   {path: 'other/admins', component:OtherAdminsComponent},
//   {path: 'free/appointments', component:FreeAppointmentsComponent},
//   {path: 'bloodsupplies', component:BloodSuppliesComponent},
//   {path: 'changePassword', component: ChangePasswordComponent},
//   {path: 'examiantion', component:ExaminationComponent},
//   {path: 'create-appointment', component: CreateAppointmentComponent},
//   {path: 'login', component: UserLoginComponent }
// ];

const routes: Routes = [
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

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
