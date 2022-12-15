import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IBloodBank } from '../model/BloodBankk';
import { Observable } from 'rxjs';
import { IPage } from '../model/Page';
import { IWorkingHours } from '../model/WorkingHours';

@Injectable({
  providedIn: 'root',
})
export class BloodBankService {
  constructor(private http: HttpClient) {}
  bloodBanks: IBloodBank[] = [];

  getBloodBanks(): Observable<IBloodBank[]> {
    return this.http.get<IBloodBank[]>(
      `http://localhost:8080/bloodbank/findAll`
    );
  }

  getBloodBanksFilterAndSearch(
    name: string,
    city: string,
    averageGrade: number,
    pageNumber: number,
    sortBy: string = 'name',
    sortDirection: string = 'ASC'
  ) {
    return this.http.get<IPage>(
      'http://localhost:8080/bloodbank/searchAndFilter?name=' +
        name +
        '&city=' +
        city +
        '&averageGrade=' +
        averageGrade +
        '&pageSize=' +
        2 +
        '&pageNumber=' +
        pageNumber +
        '&sortBy=' +
        sortBy +
        '&sortDirection=' +
        sortDirection
    );
  }

  registerBloodbank(bloodbank: any) {
    return this.http.post<IBloodBank>(
      `http://localhost:8080/bloodbank/register`,
      bloodbank
    );
  }

  getBloodBankWorkingHours() {
    return this.http.get<IWorkingHours>(
      `http://localhost:8080/bloodbank/getWorkingHours`
    );
  }

}
