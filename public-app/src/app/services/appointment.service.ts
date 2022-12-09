import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IBloodBank } from '../model/BloodBankk';
import { Observable } from 'rxjs';
import { IPage } from '../model/Page';
import { IAppointment } from '../model/Appointment';


@Injectable({
  providedIn: 'root',
})
export class AppointmentService {

  base = "http://localhost:8080/appointment"
  constructor(private http: HttpClient) {}

  createAppointment(appointment: IAppointment) : Observable<IAppointment> {
    return this.http.post<IAppointment>(`http://localhost:8080/appointment/create`, appointment);
  }

  getAppointmentsForChosenMonth(month: number, year: number): Observable<any>{
    return this.http.get(`${this.base}/appointments?month=${month}&year=${year}`)
  }
}
