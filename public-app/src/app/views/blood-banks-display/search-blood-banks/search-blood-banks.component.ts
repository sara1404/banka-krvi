import { IBloodBank } from './../../../model/BloodBankk';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BloodBankService } from '../../../services/blood-bank.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-search-blood-banks',
  templateUrl: './search-blood-banks.component.html',
  styleUrls: ['./search-blood-banks.component.scss'],
})
export class SearchBloodBanksComponent implements OnInit {
  constructor(
    private bloodBankService: BloodBankService,
    private toastService: ToastService
  ) {}

  @Input() averageGrade = 0;
  @Input() lng = 0;
  @Input() lat = 0
  @Input() distance = 6378
  @Output() bloodBanks = new EventEmitter<IBloodBank[]>();
  @Output() totalElements = new EventEmitter<number>();
  @Output() name = new EventEmitter<string>();
  @Output() city = new EventEmitter<string>();
  @Output() pageNumber = new EventEmitter<number>();
  ngOnInit(): void {
    this.bloodBankService.getLocationService().then(resp=> {
      this.lng = resp.lng
      this.lat = resp.lat
    })
  }

  searchBloodBanks(name: string, city: string, e: Event){
    e.preventDefault();
    this.bloodBankService.getBloodBanksFilterAndSearch(name, city, this.averageGrade, this.lng, this.lat, this.distance , 0).subscribe({
      next: (data) => {
        this.bloodBanks.emit(data.content)
        this.totalElements.emit(data.totalElements);
      }, error : (err) => {
        console.log("error " + err.status)
        this.toastService.showError("Error occured, status code: " + err.status)
      }
    });
    this.name.emit(name);
    this.city.emit(city);
    this.pageNumber.emit(0);
  }
}
