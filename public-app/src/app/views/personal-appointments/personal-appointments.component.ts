import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { IAppointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/services/appointment.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-personal-appointments',
  templateUrl: './personal-appointments.component.html',
  styleUrls: ['./personal-appointments.component.scss']
})
export class PersonalAppointmentsComponent implements OnInit {
  personalAppointments: MatTableDataSource<IAppointment>;
  displayedColumns:string[] = ['startTime', 'duration', 'bloodBank', 'action'];
  pageSizePersonal = 20;
  pageNumberPersonal = 0;
  constructor(private appointmentService: AppointmentService, private toastService: ToastService) { }

  @ViewChild(MatPaginator) paginatorPersonal: MatPaginator;

  ngOnInit(): void {
    this.getCurrentUserAppointments();
  }

  private getCurrentUserAppointments() {
    this.appointmentService.getPersonalAppointments(this.pageSizePersonal, this.pageNumberPersonal)
      .subscribe((response) => {
        this.personalAppointments = new MatTableDataSource<IAppointment>(response);
        this.personalAppointments.paginator = this.paginatorPersonal;
      });
  }

  cancel(id:number) {
    this.appointmentService.cancelAppointmentById(id).subscribe(
      (res) => {
        this.toastService.showSuccess('Appointment canceled!');
        this.getCurrentUserAppointments();
      },
      (err) => {
        this.toastService.showSuccess('Error: ' + err.status);
      }
    )
  }
}
