import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { IAppointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/services/appointment.service';
import { MatButton } from '@angular/material/button';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-predefined-appointments',
  templateUrl: './predefined-appointments.component.html',
  styleUrls: ['./predefined-appointments.component.scss']
})
export class PredefinedAppointmentsComponent implements OnInit {
  predefinedAppointments: MatTableDataSource<IAppointment>;
  
  displayedColumns:string[] = ['startTime', 'duration', 'bloodBank', 'action'];
  pageSizePredefined = 20;
  pageNumberPredefined = 0;
  
  length: number;
  currentPage: number;
  pageEvent: PageEvent;
  constructor(private appointmentService: AppointmentService, private toastService: ToastService) { }

  @ViewChild(MatPaginator) paginatorPredefined: MatPaginator;
  

  ngOnInit(): void { 
    this.getPredefinedAppointments();
    
  }

  private getPredefinedAppointments() {
    this.appointmentService.getPredefinedAppointments(this.pageSizePredefined, this.pageNumberPredefined)
      .subscribe((response) => {
        this.predefinedAppointments = new MatTableDataSource<IAppointment>(response);
        this.predefinedAppointments.paginator = this.paginatorPredefined;
      });
  }

  schedule(id: number) {
    this.appointmentService.scheduleAppointmentById(id).subscribe(
      (res) => {
        this.toastService.showSuccess('You just made an appointment!');
        this.getPredefinedAppointments();
      },
      (err) => {
        this.toastService.showError("Could not make an appointment, try again later! Status code: " + err.status);
      }
    )
  }


}