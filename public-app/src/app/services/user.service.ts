import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
    return this.http.get<IUser>('http://localhost:8080/user/loggedInUser/' + 1);
  }

  updateUserProfile(user: IUser) : Observable<IUser> {
    var headers = new HttpHeaders().set("Content-Type", "application/json");
    return this.http.put<IUser>(`http://localhost:8080/user/update/`, JSON.stringify(user), {headers : headers});
  }
}
