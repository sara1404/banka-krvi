import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminCalendarComponent } from './admin-calendar.component';
import { DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CalendarModule } from 'angular-calendar';
import { ClickedAppointmentComponent } from './clicked-appointment/clicked-appointment.component';
import { AppointmentInfoComponent } from './appointment-info/appointment-info.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MaterialModule } from 'src/app/material/material.module';


@NgModule({
  declarations: [
    AdminCalendarComponent,
    ClickedAppointmentComponent,
    AppointmentInfoComponent
  ],
  imports: [
    CommonModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory }),
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MaterialModule
  ]

})
export class AdminCalendarModule { }
