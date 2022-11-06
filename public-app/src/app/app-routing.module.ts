import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DisplayBloodBanksComponent } from './blood-banks-display/blood-banks-display.component';

const routes: Routes = [
  {
  path: 'bloodBanks',
  component: DisplayBloodBanksComponent,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
