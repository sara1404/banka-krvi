import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IBloodBank } from '../model/BloodBankk';
import { Observable } from 'rxjs';
import { IPage } from '../model/Page';
import { IAppointment } from '../model/Appointment';
import { IAppointmentInfo } from '../model/AppointmentInfo';
import { IAppointmentAndInfo } from '../admin-profile/model/AppointmentAndInfo';
import { IEquipment } from '../model/Equipment';


@Injectable({
  providedIn: 'root',
})
export class AppointmentInfoService {

  constructor(private http: HttpClient) {}

    createAppointmentInfo(appointmentInfo: IAppointmentAndInfo) : Observable<IAppointmentAndInfo> {
    return this.http.post<IAppointmentAndInfo>(`http://localhost:8080/appointmentinfo/create`, appointmentInfo);
    }

    usedEquipment(equipment:IEquipment):Observable<Boolean>{
      return this.http.post<Boolean>(`http://localhost:8080/equipment/used`, equipment);
    }
}