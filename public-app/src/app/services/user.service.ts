import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUser } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  user : IUser = {firstName: "ana", lastName: "vulin", jmbg: 123, bloodBank: null, bloodType: "A", email : "llalal"};
  getUsers(): Observable<IUser[]>{
    return this.http.get<IUser[]>(`http://localhost:8080/user/users`)
  }

  getRegisteredUserProfile() : IUser{
    return this.user; 
    //return this.http.get<IUser>('');
  }
}
