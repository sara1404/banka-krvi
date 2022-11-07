import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-free-appointments',
  templateUrl: './free-appointments.component.html',
  styleUrls: ['./free-appointments.component.scss']
})
export class FreeAppointmentsComponent implements OnInit {

  constructor() { }

  displayedColumns: string[] = ["start", "end"]
  ngOnInit(): void {
  }

}
