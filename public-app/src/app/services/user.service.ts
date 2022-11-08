import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUser } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  user : IUser = {id: 1,firstName: "ana", lastName: "vulin", jmbg: 123, bloodBank: null, bloodType: "A_POSITIVE", email : "vulinana@gmail.com"};
  getUsers(): Observable<IUser[]>{
    return this.http.get<IUser[]>(`http://localhost:8080/user/users`)
  }

  getRegisteredUserProfile() : IUser{
    return this.user; 
    //return this.http.get<IUser>('');
  }

  updateUserProfile(user: IUser) : Observable<IUser> {
    var headers = new HttpHeaders().set("Content-Type", "application/json");
    return this.http.put<IUser>(`http://localhost:8080/user/update/`, JSON.stringify(user), {headers : headers});
  }
}
