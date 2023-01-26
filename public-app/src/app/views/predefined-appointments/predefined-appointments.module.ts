import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';
import { MatButtonModule } from '@angular/material/button';
import { MatSortModule } from '@angular/material/sort';
import { AppointmentHistoryComponent } from './appointment-history/appointment-history.component';

@NgModule({
  declarations: [
    AppointmentHistoryComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    MatButtonModule,
    MatSortModule,
  ]
})
export class PredefinedAppointmentsModule { }
