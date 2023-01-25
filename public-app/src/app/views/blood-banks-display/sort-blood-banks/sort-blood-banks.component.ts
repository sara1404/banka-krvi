import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IBloodBank } from 'src/app/model/BloodBankk';
import { BloodBankService } from 'src/app/services/blood-bank.service';

@Component({
  selector: 'app-sort-blood-banks',
  templateUrl: './sort-blood-banks.component.html',
  styleUrls: ['./sort-blood-banks.component.scss']
})
export class SortBloodBanksComponent implements OnInit {

  @Input() name = '';
  @Input() city = '';
  @Input() averageGrade = 0;
  @Input() lng = 0
  @Input() lat = 0
  @Input() distance = 6378
  @Output() sortBy = new EventEmitter<string>();
  @Output() sortDirection = new EventEmitter<string>();
  @Output() bloodBanks = new EventEmitter<IBloodBank[]>();
  @Output() totalElements = new EventEmitter<number>();
  @Output() pageNumber = new EventEmitter<number>();
  constructor(private bloodBankService: BloodBankService) {}

  ngOnInit(): void {
  }

  sortBloodBanks(e: any) {
    var sortValueArray = e.value.split(' ');
    var sortBy = sortValueArray[0];
    var sortDirection = sortValueArray[1];
    this.bloodBankService
      .getBloodBanksFilterAndSearch(
        this.name,
        this.city,
        this.averageGrade,
        this.lng,
        this.lat,
        this.distance,
        0,
        sortBy,
        sortDirection
      )
      .subscribe((data) => {
        this.bloodBanks.emit(data.content);
        this.totalElements.emit(data.totalElements);
      });
    this.pageNumber.emit(0);
    this.sortBy.emit(sortBy);
    this.sortDirection.emit(sortDirection);
  }

}
