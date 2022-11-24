import { IBloodBank } from '../../../model/BloodBankk';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BloodBankService } from '../../../services/blood-bank.service';

@Component({
  selector: 'app-filter-blood-banks',
  templateUrl: './filter-blood-banks.component.html',
  styleUrls: ['./filter-blood-banks.component.scss'],
})
export class FilterBloodBanksComponent implements OnInit {
  constructor(private bloodBankService: BloodBankService) {}

  @Input() name = '';
  @Input() city = '';
  @Output() bloodBanks = new EventEmitter<IBloodBank[]>();
  @Output() totalElements = new EventEmitter<number>();
  @Output() averageGrade = new EventEmitter<number>();
  @Output() pageNumber = new EventEmitter<number>();
  @Output() sortBy = new EventEmitter<string>();
  @Output() sortDirection = new EventEmitter<string>();
  ngOnInit(): void {}

  
  sortAndFilterBloodBanks(e: any) {
    var averageGrade = parseFloat(e.value);
    if(averageGrade != NaN) {
      var sortBy = e.value.split(' ')[0];
      var sortDirection = e.value.split(' ')[1];
      averageGrade = 0;
    }
    else {
      sortBy = '';
      sortDirection = '';
    }
    this.bloodBankService
      .getBloodBanksFilterAndSearch(
        this.name,
        this.city,
        averageGrade,
        0,
        sortBy,
        sortDirection
      )
      .subscribe((data) => {
        this.bloodBanks.emit(data.content);
        this.totalElements.emit(data.totalElements);
      });
    this.averageGrade.emit(averageGrade);
    this.pageNumber.emit(0);
    this.sortBy.emit(sortBy);
    this.sortDirection.emit(sortDirection);
  }

}
