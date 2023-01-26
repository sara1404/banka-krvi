import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { IAppointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/services/appointment.service';

@Component({
  selector: 'app-appointment-history',
  templateUrl: './appointment-history.component.html',
  styleUrls: ['./appointment-history.component.scss']
})
export class AppointmentHistoryComponent implements OnInit {

  constructor(private service: AppointmentService) { }

  displayedColumns: string[] = ['startTime', 'duration', 'bloodBank'];
  pageSize = 20;
  pageNumber = 0;
  sortDirection = 'ASC';
  sortBy = 'startTime';

  appointments: MatTableDataSource<IAppointment>;

  @ViewChild(MatPaginator) paginatorPredefined: MatPaginator;
  
  ngOnInit(): void {
    this.getHistory();
  }

  getHistory() {
    this.service.getHistory(this.pageSize, this.pageNumber, this.sortBy, this.sortDirection).subscribe(
      (res) => {
        this.appointments = new MatTableDataSource<IAppointment>(res);
        this.appointments.paginator = this.paginatorPredefined;
      }
    )
  }

  sortHistory(e: any) {
    var sortValueArray = e.value.split(' ');
    this.sortBy = sortValueArray[0];
    this.sortDirection = sortValueArray[1];
    this.getHistory();
  }

}
