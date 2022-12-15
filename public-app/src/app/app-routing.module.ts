import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserLoginComponent } from './views/user-login/user-login/user-login.component';
import { MainAppComponent } from './components/main-app/main-app.component';

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
