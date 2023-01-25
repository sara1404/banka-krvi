import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { IAppointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/services/appointment.service';
import { MatSort, Sort } from '@angular/material/sort';
@Component({
  selector: 'app-users-given-blood',
  templateUrl: './users-given-blood.component.html',
  styleUrls: ['./users-given-blood.component.scss']
})
export class UsersGivenBloodComponent implements OnInit {

  constructor(private appointmentService: AppointmentService) { }

  appointments = new MatTableDataSource<IAppointment>();
  displayedColumns: string[] = ["firstname", "lastname", "startTime"];
  sortBy: string = "startTime";
  sortDirection: string = "ASC";
  pageNumber: number = 0;
  totalElements: number = 0;
  @ViewChild(MatSort) sort: MatSort;
  ngOnInit(): void {
    this.appointmentService.getUsersBlood(this.pageNumber, this.sortBy, this.sortDirection).subscribe({
      next: (data) => {
        this.appointments = new MatTableDataSource(data.content);
      }
    })
    console.log(this.appointments)
    console.log('nesto u blood');
  }

  saveSortBy(eventData: string) {
    this.sortBy = eventData;
    console.log('udje u sort by')
    console.log(eventData)
  }

  saveSortDirection(eventData: string) {
    this.sortDirection = eventData;
  }

  onPageChanged(e : any){
    this.pageNumber = e.pageIndex;
    this.appointmentService
      .getUsersBlood(e.pageIndex, this.sortBy, this.sortDirection)
      .subscribe({
        next: (data) => {
          this.appointments = new MatTableDataSource(data.content);
          this.totalElements = data.totalElements;
        },
        error: (err) => {
          console.log("error " + err.status)
          
        }
      });
  }

  savePageNumber(eventData: number){
    this.pageNumber = eventData;
  }

  saveTotalElements(eventData: number){
    this.totalElements = eventData;
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
        this.appointments = new MatTableDataSource(data.content);
        this.totalElements = data.totalElements;
      });
    this.pageNumber = 0;
    this.sortBy = sortBy;
    this.sortDirection = sortDirection;
  }
}
