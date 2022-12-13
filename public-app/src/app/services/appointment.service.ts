import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IBloodBank } from '../model/BloodBankk';
import { Observable } from 'rxjs';
import { IPage, IPageAppointment } from '../model/Page';
import { IAppointment } from '../model/Appointment';


@Injectable({
  providedIn: 'root',
})
export class AppointmentService {

  constructor(private http: HttpClient) {}

    createAppointment(appointment: IAppointment) : Observable<IAppointment> {
      var headers = new HttpHeaders().set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    return this.http.post<IAppointment>(`http://localhost:8080/appointment/create`, appointment, {headers: headers});
  }

  getBloodBanksWithFreeTimeSlots(startTime: Date, pageNumber: number, sortDirection: string){
    return this.http.get<IPageAppointment>(
      `http://localhost:8080/appointment/recommend?startTime=` + startTime + '&pageSize=' + 2 + '&pageNumber=' + pageNumber + '&sortDirection=' + sortDirection   
    );
  }

  
  scheduleAppointment(appointment: any): Observable<IAppointment> {
    var headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    return this.http.put<IAppointment>(
      `http://localhost:8080/appointment/schedule`,
      JSON.stringify(appointment),
      { headers: headers }
    );
  }
}