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
    //this.bloodBanks.push({name: "lala", address: {street: "Rumenacka", number: 1, city: "Beograd", zipcode: 11111, country:"Srbija", longitude: 0, latitude: 0}, description: "lalalal", averageGrade:4});
    //this.bloodBanks.push({name: "lala", address: {street: "Rumenacka", number: 1, city: "Beograd", zipcode: 11111, country:"Srbija", longitude: 0, latitude: 0}, description: "lalalal", averageGrade:4});
    //return this.bloodBanks;
    return this.http.get<IBloodBank[]>(
      `localhost:8080/bloodbank/findAll`
    );
  }

  searchFilterBloodBanks(name: string, city: string, averageGrade: number) : IBloodBank[] {
    this.bloodBanks.pop();
    return this.bloodBanks;
  }
}
