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
  @Output() totalElements = new EventEmitter<number>();
  @Output() averageGrade = new EventEmitter<number>();
  @Output() pageNumber = new EventEmitter<number>();
  ngOnInit(): void {}

  filterBloodBanks(e: any){
    var averageGrade = parseFloat(e.value);
    this.bloodBankService.getBloodBanksFilterAndSearch(this.name, this.city, averageGrade, 0).subscribe((data) => {
    this.bloodBanks.emit(data.content)
    this.totalElements.emit(data.totalElements);
    });
    this.averageGrade.emit(averageGrade);
    this.pageNumber.emit(0);
  }
}
