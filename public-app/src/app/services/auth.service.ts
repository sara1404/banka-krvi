import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICredentials } from '../model/Credentials';
import { IToken } from '../model/Token';

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

  logout() {
    localStorage.removeItem('token');
  }

  public isLoggedIn() {
    return localStorage.getItem('token') != null;
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

}
