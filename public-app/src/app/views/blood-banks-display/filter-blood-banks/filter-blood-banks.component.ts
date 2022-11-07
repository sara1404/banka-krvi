import { IBloodBank } from '../../../model/BloodBankk';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BloodBankService } from '../../../services/blood-bank.service';

@Component({
  selector: 'app-filter-blood-banks',
  templateUrl: './filter-blood-banks.component.html',
  styleUrls: ['./filter-blood-banks.component.scss'],
})
export class FilterBloodBanksComponent implements OnInit {
  constructor(
    private bloodBankService: BloodBankService,
  ) {}

  @Input() name = "";
  @Input() city = "";
  @Output() bloodBanks = new EventEmitter<IBloodBank[]>();
  @Output() averageGrade = new EventEmitter<number>();
  ngOnInit(): void {}

  filterBloodBanks(e: any){
    var averageGrade = parseFloat(e.value);
    this.bloodBankService.searchFilterBloodBanks(this.name, this.city, averageGrade).subscribe((data) => this.bloodBanks.emit(data));
    this.averageGrade.emit(averageGrade);
  }
}
