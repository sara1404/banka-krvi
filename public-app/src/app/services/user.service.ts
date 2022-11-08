import { HttpClient } from '@angular/common/http';
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

  search(value:string): Observable<IUser[]>{
    let firstName = value.split(',')[0]?.trim() === undefined ? "" : value.split(',')[0]?.trim()
    let lastName = value.split(',')[1]?.trim() === undefined ? "" : value.split(',')[1]?.trim()
    let users = this.http.get<IUser[]>(`http://localhost:8080/user/search?name=${firstName}&surname=${lastName}`)
    console.log(firstName, lastName)
    return users
  }

}
