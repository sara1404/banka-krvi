import { IBloodBank } from '../../../model/BloodBankk';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BloodBankService } from '../../../services/blood-bank.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-filter-blood-banks',
  templateUrl: './filter-blood-banks.component.html',
  styleUrls: ['./filter-blood-banks.component.scss'],
})
export class FilterBloodBanksComponent implements OnInit {
  constructor(private bloodBankService: BloodBankService, private toastService: ToastService) {}

  @Input() name = '';
  @Input() city = '';
  @Input() lng = 0;
  @Input() lat = 0
  @Input() distance = 6378 
  @Input() sortBy = '';
  @Input() sortDirection = '';
  @Output() bloodBanks = new EventEmitter<IBloodBank[]>();
  @Output() totalElements = new EventEmitter<number>();
  @Output() averageGrade = new EventEmitter<number>();
  @Output() pageNumber = new EventEmitter<number>();

  ngOnInit(): void {}

  filterBloodBanks(e: any) {
    var averageGrade = parseFloat(e.value);
    this.bloodBankService
      .getBloodBanksFilterAndSearch(
        this.name,
        this.city,
        averageGrade,
        this.lng,
        this.lat,
        this.distance,
        0,
        this.sortBy,
        this.sortDirection
      )
      .subscribe({
        next : (data) => {
        this.bloodBanks.emit(data.content);
        this.totalElements.emit(data.totalElements);
        }, error : (err) => {
          console.log("error " + err.status)
          this.toastService.showError("Error occured, status code: " + err.status)
        }
      });
    this.averageGrade.emit(averageGrade);
    this.pageNumber.emit(0);
  }
}
