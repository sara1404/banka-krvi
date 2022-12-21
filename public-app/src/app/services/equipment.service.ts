import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IBloodBank } from '../model/BloodBankk';
import { Observable } from 'rxjs';
import { IPage } from '../model/Page';
import { IWorkingHours } from '../model/WorkingHours';
import { IEquipment } from '../model/Equipment';

@Injectable({
  providedIn: 'root',
})
export class EquipmentService {
  constructor(private http: HttpClient) {}

  addNewEquipment(equipment: IEquipment): Observable<IEquipment>{
    console.log(equipment)
    var headers = new HttpHeaders().set('Authorization',  `Bearer ${localStorage.getItem('token')}`);
    return this.http.post<IEquipment>(`http://localhost:8080/equipment/create`, equipment, {
      headers: headers
    });
  }

}
