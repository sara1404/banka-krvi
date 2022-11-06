import { IBloodBank } from '../../model/BloodBank';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BloodBankService } from '../../service/blood-bank-service.service';

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
    
  ngOnInit(): void {}

  filterBloodBanks(e: any){
    var averageGrade = parseFloat(e.value);
    this.bloodBanks.emit(this.bloodBankService.searchFilterBloodBanks(this.name, this.city, averageGrade));
  }
}
