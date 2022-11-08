import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IBloodBank } from '../model/BloodBank';
import { IUser } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class AdminInfoService {

  constructor(private http: HttpClient) { }

  getUser(userId: number): Observable<IUser>{
    //zakucano na 3, to treba da je logovan
    return this.http.get<IUser>(`http://localhost:8080/user/3/`)
  }

  
  editUser(user: IUser)
  {
    return this.http.patch<IUser>('http://localhost:8080/user/update/', user);
  }
  
  getBloodBank():Observable<IBloodBank>{
    return this.http.get<IBloodBank>('http://localhost:8080/bloodbank/administrator/');
  }
}