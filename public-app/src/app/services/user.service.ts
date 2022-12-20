import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IDonationSurvey } from '../model/DonationSurvey';
import { IUserSurvey } from '../model/UserSurvey';
import { IUser } from '../model/User';
import { IUserAppointment } from '../model/UserAppointment';
import { Token } from '@angular/compiler';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getUsers(pageNo: number): Observable<IUser[]> {
    return this.http.get<IUser[]>(`http://localhost:8080/user/users/${pageNo}`);
  }

  getUsersCount(): Observable<number> {
    return this.http.get<number>(`http://localhost:8080/user/users/count`);
  }

  getLoggedInUserProfile(): Observable<IUser> {
    var header = {
      headers: new HttpHeaders()
        .set('Authorization',  `Bearer ${localStorage.getItem('token')}`)
    }
    return this.http.get<IUser>(
      'http://localhost:8080/user/getUserProfile', header
    );
  }

  updateUserProfile(user: any): Observable<IUser> {
    var headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.put<IUser>(
      `http://localhost:8080/user/update/`,
      JSON.stringify(user),
      { headers: headers }
    );
  }
  search(value: string): Observable<IUser[]> {
    let firstName =
      value.split(',')[0]?.trim() === undefined
        ? ''
        : value.split(',')[0]?.trim();
    let lastName =
      value.split(',')[1]?.trim() === undefined
        ? ''
        : value.split(',')[1]?.trim();
    let users = this.http.get<IUser[]>(
      `http://localhost:8080/user/search?name=${firstName}&surname=${lastName}`
    );
    console.log(firstName, lastName);
    return users;
  }

  registerAdmin(user: any): Observable<IUser> {
    return this.http.post<IUser>(
      `http://localhost:8080/user/register/admin`,
      user
    );
  }

  registerUser(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(`http://localhost:8080/auth/register`, user);
  }

  confirmUserRegistration(email: string) {
    return this.http.post(`http://localhost:8080/user/activate/${email}`, null);
  }

  sendSurvey(survey: IDonationSurvey): Observable<IDonationSurvey> {
    return this.http.post<IDonationSurvey>(
      `http://localhost:8080/survey/fill`,
      survey
    );
  }

  getAvailableCenterAdmins(): Observable<IUser[]> {
    return this.http.get<IUser[]>(`http://localhost:8080/user/center-admins`);
  }

  getSurveyForUser(userId: number):Observable<IUserSurvey>{
    return this.http.get<IUserSurvey>(`http://localhost:8080/survey/for-user/${userId}`);
  }

  getAppointmentsForUser(userId: number):Observable<IUserAppointment[]>{
    return this.http.get<IUserAppointment[]>(`http://localhost:8080/appointment/for-user/${userId}`);
  }

  addPenalPoints(userId: number):Observable<Boolean>{
    return this.http.post<Boolean>('http://localhost:8080/user/penal-points', userId);
  }

  finishAppointment(appointmentId: number):Observable<Boolean>{
    return this.http.post<Boolean>('http://localhost:8080/appointment/finish', appointmentId);
  }
  getUser(userId: number): Observable<IUser>{
    return this.http.get<IUser>(`http://localhost:8080/user/${userId}`);
  }


}
