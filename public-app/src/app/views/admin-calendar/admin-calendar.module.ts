import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminCalendarComponent } from './admin-calendar.component';
import { DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CalendarModule } from 'angular-calendar';


@NgModule({
  declarations: [
    AdminCalendarComponent
  ],
  imports: [
    CommonModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory })
  ]

})
export class AdminCalendarModule { }
