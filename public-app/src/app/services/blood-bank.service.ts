import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IBloodBank } from '../model/BloodBankk';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BloodBankService {
  constructor(private http: HttpClient) {}
  bloodBanks: IBloodBank[] = []

  getBloodBanks() : Observable<IBloodBank[]> {
    return this.http.get<IBloodBank[]>(
      `http://localhost:8080/bloodbank/findAll`
    );
  }

  searchFilterBloodBanks(name: string, city: string, averageGrade: number) {
    return this.http.get<IBloodBank[]>(
      'http://localhost:8080/bloodbank/searchAndFilter?name=' + name + '&city=' + city + '&averageGrade=' + averageGrade
      );
  }

}
