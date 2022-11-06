import { IBloodBank } from './../../model/BloodBank';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BloodBankService } from '../../service/blood-bank-service.service';

@Component({
  selector: 'app-search-blood-banks',
  templateUrl: './search-blood-banks.component.html',
  styleUrls: ['./search-blood-banks.component.scss'],
})
export class SearchBloodBanksComponent implements OnInit {
  constructor(
    private bloodBankService: BloodBankService,
  ) {}

  @Output() bloodBanks = new EventEmitter<IBloodBank[]>();
  @Output() name = new EventEmitter<string>();
  @Output() city = new EventEmitter<string>();
  
  ngOnInit(): void {}

  searchBloodBanks(name: string, city: string, e: Event){
    e.preventDefault();
    this.bloodBanks.emit(this.bloodBankService.searchFilterBloodBanks(name, city, 0));
    this.name.emit(name);
    this.city.emit(city);
  }
}
