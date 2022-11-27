import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-calendar',
  templateUrl: './admin-calendar.component.html',
  styleUrls: ['./admin-calendar.component.scss']
})
export class AdminCalendarComponent implements OnInit {

  viewDate: any
  events = []
  constructor() {
    this.viewDate = Date.now()
   }

  ngOnInit(): void {
  }

}
