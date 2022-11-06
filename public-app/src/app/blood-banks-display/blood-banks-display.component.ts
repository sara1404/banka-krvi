import { Component } from '@angular/core';
import { IBloodBank } from '../model/BloodBank';
import { BloodBankService } from '../service/blood-bank-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './blood-banks-display.component.html',
  styleUrls: ['./blood-banks-display.component.css']
})
export class DisplayBloodBanksComponent {
  constructor(
    private bloodBankService: BloodBankService,
  ) {}

  bloodBanks: IBloodBank[] = [];
  name: string = ""
  city: string = ""

  ngOnInit(): void {
    /*this.bloodBankService
      .getBloodBanks()
      .subscribe((data) => (this.bloodBanks = data));*/
      this.bloodBanks = this.bloodBankService.getBloodBanks();
  }

  onSearch(eventData: IBloodBank[]) {
    this.bloodBanks = eventData;   
  }

  saveSearchName(eventData: string){
    this.name = eventData;
  }
  saveSearchCity(eventData: string){
    this.city = eventData;
  }
}
