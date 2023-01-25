import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IAppointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/services/appointment.service';

@Component({
  selector: 'app-sort-users-blood',
  templateUrl: './sort-users-blood.component.html',
  styleUrls: ['./sort-users-blood.component.scss']
})
export class SortUsersBloodComponent implements OnInit {

  @Output() sortBy = new EventEmitter<string>();
  @Output() sortDirection = new EventEmitter<string>();
  @Output() appointments = new EventEmitter<IAppointment[]>();
  @Output() totalElements = new EventEmitter<number>();
  @Output() pageNumber = new EventEmitter<number>();

  constructor(private appointmentService: AppointmentService) { }

  ngOnInit(): void {
  }
  sortAppointments(e: any) {
    var sortValueArray = e.value.split(' ');
    var sortBy = sortValueArray[0];
    var sortDirection = sortValueArray[1];
    this.appointmentService
      .getUsersBlood(
        0,
        sortBy,
        sortDirection
      )
      .subscribe((data) => {
        this.appointments.emit(data.content);
        this.totalElements.emit(data.totalElements);
      });
    this.pageNumber.emit(0);
    this.sortBy.emit(sortBy);
    this.sortDirection.emit(sortDirection);
  }
}
