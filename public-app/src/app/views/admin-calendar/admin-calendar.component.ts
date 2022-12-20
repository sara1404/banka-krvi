import { Component, OnInit } from '@angular/core';
import { CalendarEvent, CalendarView } from 'angular-calendar';
import { IUserAppointment } from 'src/app/model/UserAppointment';
import { AppointmentService } from 'src/app/services/appointment.service';
import { EventColor } from 'calendar-utils';
import { map, Subject, Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { addDays, subDays, addMinutes } from 'date-fns';
import { ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ClickedAppointmentComponent } from './clicked-appointment/clicked-appointment.component';
import { UserService } from 'src/app/services/user.service';
import { IUser } from 'src/app/model/User';

const colors: Record<string, EventColor> = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#0E4C92',
    secondary: '#cbcbd226',
  },
  green: {
    primary: '#0b6623',
    secondary: '#e8fde7',
  },
};

@Component({
  selector: 'app-admin-calendar',
  templateUrl: './admin-calendar.component.html',
  styleUrls: ['./admin-calendar.component.scss'],
})
export class AdminCalendarComponent implements OnInit {



  viewDate: Date
  viewDateEnd: Date
  dayStartHour = 7
  dayEndHour = 23
  hourSegmentHeight = 80
  daysInWeek = 7
  view: CalendarView = CalendarView.Week;
  CalendarView = CalendarView;
  //appointments: IAppointment[] = []
  events: CalendarEvent<{ appointment: IUserAppointment}>[] = []
  user: IUser

  selectedEvent: CalendarEvent<{ appointment: IUserAppointment }> = {
    title: null as any,
    start: null as any,
    color: { ...colors['blue'] },
    end: null as any,
    meta: null as any
  };

  constructor(private appointmentService: AppointmentService, private userService: UserService, public dialog: MatDialog) {
    this.viewDate = new Date(Date.now())
   }

  ngOnInit(): void {
    this.viewDate = new Date('2022-12-05');
    this.viewDateEnd = addDays(this.viewDate, 6);
    this.getAppointments()
  }

  getAppointments(){
    console.log(this.viewDate.getMonth(), this.viewDate.getFullYear())
    this.appointmentService.getAppointmentsForChosenMonth(this.viewDate.getMonth() + 1, this.viewDate.getFullYear())
    .pipe(
      map((results: IUserAppointment[]) => {
      return results.map((appointment: IUserAppointment) => {
        return {
          title: this.createTitle(appointment),
          start: new Date(appointment.startTime),
          color: { ...colors['blue'] },
          end: addMinutes(new Date(appointment.startTime), appointment.duration),
          meta: {
            appointment,
          },
        };
      });
    }))


    .subscribe(
      (response: CalendarEvent<{ appointment: IUserAppointment }>[]) => {
        this.events = response;
        console.log(this.events)
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  createTitle(appointment: IUserAppointment): string {
    return (
      "Start: " + new Date(appointment.startTime) + '\n' +
      "End: " + addMinutes(new Date(appointment.startTime), appointment.duration) + '\n' +
      "User: " + appointment?.user?.firstName + " " + appointment?.user?.lastName
    );
  }

  // previousMonth(){
  //   this.viewDate = new Date(this.viewDate.setMonth((this.viewDate.getMonth() + 1) - 1));
  // }

  // nextMonth(){
  //   this.viewDate = new Date(this.viewDate.setMonth((this.viewDate.getMonth() + 1) + 1));
  // }

  previousWeek(){
    this.viewDate = subDays(this.viewDate, 7)
    this.getAppointments()
  }

  nextWeek(){
    this.viewDate = addDays(this.viewDate, 7)
    this.getAppointments()
  }

  click({ event }: { event: CalendarEvent }){
    console.log('kliknuo');
    console.log(event.meta.appointment)
    this.userService.getUser(event.meta.appointment.user.id).subscribe(data => this.user = data);

    this.dialog.open(ClickedAppointmentComponent,
      {
        data: {appointment: event.meta.appointment, user:this.user}
      });
  }
}
