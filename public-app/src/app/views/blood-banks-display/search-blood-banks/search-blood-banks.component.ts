import { IBloodBank } from './../../../model/BloodBankk';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BloodBankService } from '../../../services/blood-bank.service';

@Component({
  selector: 'app-search-blood-banks',
  templateUrl: './search-blood-banks.component.html',
  styleUrls: ['./search-blood-banks.component.scss'],
})
export class SearchBloodBanksComponent implements OnInit {
  constructor(
    private bloodBankService: BloodBankService,
  ) {}

  @Input() averageGrade = 0;
  @Output() bloodBanks = new EventEmitter<IBloodBank[]>();
  @Output() name = new EventEmitter<string>();
  @Output() city = new EventEmitter<string>();
  ngOnInit(): void {}

  searchBloodBanks(name: string, city: string, e: Event){
    e.preventDefault();
    this.bloodBankService.searchFilterBloodBanks(name, city, this.averageGrade).subscribe((data) => this.bloodBanks.emit(data));
    this.name.emit(name);
    this.city.emit(city);
  }
}
