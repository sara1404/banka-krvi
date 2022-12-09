import { Component, OnInit } from '@angular/core';
import { CalendarEvent, CalendarView } from 'angular-calendar';
import { IAppointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/services/appointment.service';
import { EventColor } from 'calendar-utils';
import { map, Subject, Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { addDays, subDays, addMinutes } from 'date-fns';


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
  styleUrls: ['./admin-calendar.component.scss']
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
  events: CalendarEvent<{ appointment: IAppointment}>[] = []

  selectedEvent: CalendarEvent<{ appointment: IAppointment }> = {
    title: null as any,
    start: null as any,
    color: { ...colors['blue'] },
    end: null as any,
    meta: null as any,
  };

  constructor(private appointmentService: AppointmentService) {
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
      map((results: IAppointment[]) => {
      return results.map((appointment: IAppointment) => {
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
      (response: CalendarEvent<{ appointment: IAppointment }>[]) => {
        this.events = response;
        console.log(this.events)
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  createTitle(appointment: IAppointment): string {
    return (
      "Start: " + new Date(appointment.startTime) + '\n' +
      "End: " + addMinutes(new Date(appointment.startTime), appointment.duration) + '\n' +
      "User: " + "fiksni"
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
}
