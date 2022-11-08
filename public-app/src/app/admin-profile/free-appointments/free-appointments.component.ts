import { Component, OnInit } from '@angular/core';
import { IAppointment } from '../model/Appointment';
import { AdminInfoService } from '../service/admin-info.service';

@Component({
  selector: 'app-free-appointments',
  templateUrl: './free-appointments.component.html',
  styleUrls: ['./free-appointments.component.scss']
})
export class FreeAppointmentsComponent implements OnInit {

  constructor(private adminInfoService: AdminInfoService) { }

  displayedColumns: string[] = ["start", "end"]
  availableAppointments: IAppointment[];
  ngOnInit(): void {
    this.adminInfoService.getAvailableAppointments().subscribe(data=>this.availableAppointments=data);
  }

}
