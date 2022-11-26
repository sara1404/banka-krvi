import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IDonationSurvey } from '../model/DonationSurvey';
import { IUserSurvey } from '../model/UserSurvey';
import { IUser } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUsers(): Observable<IUser[]>{
    return this.http.get<IUser[]>(`http://localhost:8080/user/users`)
  }

  getLoggedInUserProfile() : Observable<IUser> {
    return this.http.get<IUser>('http://localhost:8080/user/loggedInUser/' + 10);
  }

  updateUserProfile(user: any) : Observable<IUser> {
    var headers = new HttpHeaders().set("Content-Type", "application/json");
    return this.http.put<IUser>(`http://localhost:8080/user/update/`, JSON.stringify(user), {headers : headers});
  }
  search(value:string): Observable<IUser[]>{
    let firstName = value.split(',')[0]?.trim() === undefined ? "" : value.split(',')[0]?.trim()
    let lastName = value.split(',')[1]?.trim() === undefined ? "" : value.split(',')[1]?.trim()
    let users = this.http.get<IUser[]>(`http://localhost:8080/user/search?name=${firstName}&surname=${lastName}`)
    console.log(firstName, lastName)
    return users
  }

  registerAdmin(user: any) : Observable<IUser>{
    return this.http.post<IUser>(`http://localhost:8080/user/register/admin`, user)
  }

  registerUser(user: IUser) : Observable<IUser>{
    return this.http.post<IUser>(`http://localhost:8080/auth/register`, user)
  }

  sendSurvey(survey: IDonationSurvey) : Observable<IDonationSurvey> {
    return this.http.post<IDonationSurvey>(`http://localhost:8080/survey/fill`, survey);
  }

  getAvailableCenterAdmins(): Observable<IUser[]>{
    return this.http.get<IUser[]>(`http://localhost:8080/user/center-admins`)
  }

  getSurveyForUser(userId: number):Observable<IUserSurvey>{
    return this.http.get<IUserSurvey>(`http://localhost:8080/survey/${userId}`);
  }
}
