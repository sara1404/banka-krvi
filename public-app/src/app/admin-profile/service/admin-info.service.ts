import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IAdministrator } from '../model/Administrator';
import { IFreeAppointment } from '../model/FreeAppointment';
import { IAppointment } from '../model/Appointment';
import { IBloodBank } from '../model/BloodBank';
import { IBloodSupply } from '../model/BloodSupply';
import { IUser } from '../model/User';
import { IPasswordChange } from '../model/PasswordChange';
import { AuthService } from 'src/app/services/auth.service';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminInfoService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUser(userId: number): Observable<IUser>{
    //zakucano na 3, to treba da je logovan
    return this.http.get<IUser>(`http://localhost:8080/user/${userId}`);
  }

  editUser(user: IUser)
  {
    return this.http.put<IUser>('http://localhost:8080/user/update/', user);
  }

  getBloodBank():Observable<IBloodBank>{
    return this.http.get<IBloodBank>('http://localhost:8080/bloodbank/administrator');
  }

  editBloodBank(bloodBank: IBloodBank){
    return this.http.put<IBloodBank>('http://localhost:8080/bloodbank/update', bloodBank);
  }

  getOtherAdministrators(): Observable<IAdministrator[]>{
    //zakucano na 5 jer ce se preuzimati iz logovanog korisnika
    return this.http.get<IAdministrator[]>('http://localhost:8080/user/bloodBankId')
  }

  getAvailableAppointments():Observable<IFreeAppointment[]>{
    return this.http.get<IFreeAppointment[]>('http://localhost:8080/appointment/available');
  };

  getBloodSupplies():Observable<IBloodSupply[]>{
    //popravi da ne bude 5!!!
    return this.http.get<IBloodSupply[]>('http://localhost:8080/bloodsupply/bloodbank');
  }

  changePassword(passwordChange: IPasswordChange):Observable<Boolean>{
    var headers = new HttpHeaders().set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    //headers.set('Content-Type', 'application/json');
    return this.http.put<Boolean>('http://localhost:8080/user/change-password', passwordChange, { headers: headers });
  }
}
