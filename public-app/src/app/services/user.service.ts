import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IDonationSurvey } from '../model/DonationSurvey';
import { IUser } from '../model/User';
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

  sendSurvey(survey: IDonationSurvey): Observable<IDonationSurvey> {
    return this.http.post<IDonationSurvey>(
      `http://localhost:8080/survey/fill`,
      survey
    );
  }

  getAvailableCenterAdmins(): Observable<IUser[]> {
    return this.http.get<IUser[]>(`http://localhost:8080/user/center-admins`);
  }

}
