import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserLoginComponent } from './views/user-login/user-login/user-login.component';
import { MainAppComponent } from './components/main-app/main-app.component';
import { RegisterUserComponent } from './views/register-user/register-user/register-user.component';
import { DisplayBloodBanksComponent } from './views/blood-banks-display/blood-banks-display.component';

const routes: Routes = [
  {
    path: '',
    component: MainAppComponent, //UserLoginComponent,
    loadChildren: () =>
      import('./user-routing/user-routing.module').then(
        (x) => x.UserRoutingModule
      ),
  },
  {
    path: 'app',
    component: MainAppComponent,
    loadChildren: () =>
      import('./user-routing/user-routing.module').then(
        (x) => x.UserRoutingModule
      ),
  },
  {
    path: 'login',
    component: UserLoginComponent
  },
  /*
  {
    path: 'register/user/login',
    component: UserLoginComponent
  },
  {
    path: 'register/user',
    component: MainAppComponent,
    loadChildren: () =>
      import('./user-routing/user-routing.module').then(
        (x) => x.UserRoutingModule
      )
  },
  {
    path: 'bloodBanks',
    component: MainAppComponent,
    loadChildren: () =>
      import('./user-routing/user-routing.module').then(
        (x) => x.UserRoutingModule
      )
  }*/
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})

export class AppRoutingModule { }
