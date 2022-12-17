import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICredentials } from '../model/Credentials';
import { IToken } from '../model/Token';
import { IUser } from '../model/User';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  loginUser(credentials: ICredentials): Observable<IToken> {
    return this.http
      .post<IToken>(`http://localhost:8080/auth/login`, credentials);
  }

  public setSession(token: string) {
    localStorage.setItem('token', token);
  }

  public getToken() {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
  }

  public isLoggedIn() {
    return localStorage.getItem('token') != null;
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getLoggedUser():Observable<IUser>{
    var headers = new HttpHeaders().set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    //headers.set('Content-Type', 'application/json');
    return this.http.get<IUser>('http://localhost:8080/user/current', { headers: headers });
  }

}
