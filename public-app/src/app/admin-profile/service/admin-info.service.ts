import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IAppointment } from '../model/Appointment';
import { IBloodBank } from '../model/BloodBank';
import { IBloodSupply } from '../model/BloodSupply';
import { IUser } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class AdminInfoService {

  constructor(private http: HttpClient) { }

  getUser(userId: number): Observable<IUser>{
    //zakucano na 3, to treba da je logovan
    return this.http.get<IUser>(`http://localhost:8080/user/3`)
  }

  editUser(user: IUser)
  {
    return this.http.put<IUser>('http://localhost:8080/user/update', user);
  }
  
  getBloodBank():Observable<IBloodBank>{
    return this.http.get<IBloodBank>('http://localhost:8080/bloodbank/administrator');
  }

  editBloodBank(bloodBank: IBloodBank){
    return this.http.put<IBloodBank>('http://localhost:8080/bloodbank/update', bloodBank);
  }

  getOtherAdministrators(): Observable<IUser[]>{
    //zakucano na 5 jer ce se preuzimati iz logovanog korisnika
    return this.http.get<IUser[]>('http://localhost:8080/user/bloodBankId')
  }

  getAvailableAppointments():Observable<IAppointment[]>{
    return this.http.get<IAppointment[]>('http://localhost:8080/appointment/available');
  };

  getBloodSupplies():Observable<IBloodSupply[]>{
    //popravi da ne bude 5!!!
    return this.http.get<IBloodSupply[]>('http://localhost:8080/bloodsupply/bloodbank');
  }
}