import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IBloodBank } from '../model/BloodBankk';
import { Observable } from 'rxjs';
import { IPage } from '../model/Page';
import { IAppointment } from '../model/Appointment';
import { IAppointmentInfo } from '../model/AppointmentInfo';


@Injectable({
  providedIn: 'root',
})
export class AppointmentInfoService {

  constructor(private http: HttpClient) {}

    createAppointmentInfo(appointmentInfo: IAppointmentInfo) : Observable<IAppointmentInfo> {
    return this.http.post<IAppointmentInfo>(`http://localhost:8080/appointmentinfo/create`, appointmentInfo);
  }
}