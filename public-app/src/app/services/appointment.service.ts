import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IBloodBank } from '../model/BloodBankk';
import { Observable } from 'rxjs';
import { IPage, IPageAppointment, IPageUser } from '../model/Page';
import { IAppointment } from '../model/Appointment';
import { IUser } from '../model/User';


@Injectable({
  providedIn: 'root',
})
export class AppointmentService {

  base = "http://localhost:8080/appointment"
  constructor(private http: HttpClient) {}

  getAppointmentsForChosenMonth(month: number, year: number): Observable<any>{
    var headers = new HttpHeaders().set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    return this.http.get<any>(`${this.base}/appointments?month=${month}&year=${year}`, {headers: headers})
  }

  createAppointment(appointment: IAppointment) : Observable<IAppointment> {
      var headers = new HttpHeaders().set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    return this.http.post<IAppointment>(`http://localhost:8080/appointment/create`, appointment, {headers: headers});
  }

  recommendMedicalStaff(startTime: Date, duration: number) : Observable<IUser[]> {
    var headers = new HttpHeaders().set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
  return this.http.get<IUser[]>(`http://localhost:8080/appointment/getAvailableMedicalStaff?startTime=` + startTime + "&duration=" + duration , {headers: headers});
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

  getBloodBanksWithAvailableMedicalStaff(startTime: Date, duration: number, pageNumber: number, sortDirection: string) : Observable<IPageUser> {
    return this.http.get<IPageUser>(
      `http://localhost:8080/appointment/findBloodBanksWithAvailableMedicalStaff?startTime=` + startTime  + '&duration=' + duration + '&pageSize=' + 2 + '&pageNumber=' + pageNumber +  '&sortDirection=' + sortDirection
    );
  }

  userCreates(appointment: any): Observable<IAppointment> {
    var headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    return this.http.post<IAppointment>(
      `http://localhost:8080/appointment/userCreates`,
      JSON.stringify(appointment),
      { headers: headers }
    );
  }

  getPredefinedAppointments(pageSize: number, pageNumber: number, direction: string) : Observable<IAppointment[]> {
    return this.http.get<IAppointment[]>(`http://localhost:8080/appointment/predefined?pageSize=${pageSize}&pageNum=${pageNumber}&direction=${direction}`);
  }

  getPersonalAppointments(pageSize: number, pageNumber: number) : Observable<IAppointment[]> {
    return this.http.get<IAppointment[]>(`http://localhost:8080/appointment/personal?pageSize=${pageSize}&pageNum=${pageNumber}`);
  }

  scheduleAppointmentById(id: number) : Observable<IAppointment[]> {
    return this.http.put<IAppointment[]>(`http://localhost:8080/appointment/schedule/${id}`, null);
  }

  cancelAppointmentById(id: number){
    return this.http.put(`http://localhost:8080/appointment/cancel/${id}`, null);
  }
  canUserScheduleAppointment(startTime: Date) {
    var headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    return this.http.get<Boolean>(
      `http://localhost:8080/appointment/canUserScheduleAppointment?startTime=` + startTime,
      { headers: headers }
    );
  }

  getById(id: string): Observable<IAppointment>{
    var headers = new HttpHeaders().set('Content-Type', 'application/json').set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    return this.http.get<IAppointment>(`http://localhost:8080/appointment/${id}`, { headers: headers })
  }

  getUsersBlood(
    pageNumber: number,
    sortBy: string = '',
    sortDirection: string = 'ASC'
  ) {
    return this.http.get<IPageAppointment>(
      `http://localhost:8080/appointment/bloodSort?pageSize=` + 2 + '&pageNumber=' + pageNumber + '&sortDirection=' + sortDirection + '&sortBy=' + sortBy
    );
  }

  getHistory(
    pageSize: number,
    pageNumber: number,
    sortBy: string = '',
    sortDirection: string = 'ASC',

  ) {
    return this.http.get<IAppointment[]>(`http://localhost:8080/appointment/history?pageSize=${pageSize}&pageNumber=${pageNumber}&sortDirection=${sortDirection}&sortBy=${sortBy}`);
  }
}
