import { Component, OnInit, ViewChild } from '@angular/core';
import { IBloodBank } from '../../model/BloodBankk';
import { BloodBankService } from '../../services/blood-bank.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort } from '@angular/material/sort';
import { MatSortHeader } from '@angular/material/sort';

@Component({
  selector: 'app-root',
  templateUrl: './blood-banks-display.component.html',
  styleUrls: ['./blood-banks-display.component.scss']
})
export class DisplayBloodBanksComponent implements OnInit {
  constructor(
    private bloodBankService: BloodBankService,
  ) {}

  bloodBanks = new MatTableDataSource<IBloodBank>();
  displayedColumns: string[] = ["name", "street", "number", "city", "zipcode", "country", "averageGrade"]
  name: string = ""
  city: string = ""
  averageGrade: number = 0
  pageNumber: number = 0
  totalElements: number = 0

  @ViewChild(MatSort) sort: MatSort;
  
  ngOnInit(): void {
    this.bloodBankService
      .getBloodBanksFilterAndSearch(this.name, this.city, this.averageGrade, this.pageNumber)
      .subscribe((data) => {
        this.bloodBanks = new MatTableDataSource(data.content);
        this.totalElements = data.totalElements;
        // fun fact: MatSort with nested objects is not supported by default
        this.bloodBanks.sortingDataAccessor = (item, property) => {
          switch(property) {
            case 'city': return item.address.city;
            default: return item[property];
          }
        };
        this.bloodBanks.sort = this.sort;
      });
      
  }

  onSearch(eventData: IBloodBank[]) {
    this.bloodBanks.data = eventData;
  }

  saveSearchName(eventData: string){
    this.name = eventData;
  }
  saveSearchCity(eventData: string){
    this.city = eventData;
  }

  saveAverageGrade(eventData: number){
    this.averageGrade = eventData;
  }

  onPageChanged(e : any){
    this.pageNumber = e.pageIndex;
    this.bloodBankService
      .getBloodBanksFilterAndSearch(this.name, this.city, this.averageGrade, e.pageIndex)
      .subscribe((data) => {
        this.bloodBanks = new MatTableDataSource(data.content);
        this.totalElements = data.totalElements;
      });
  }

  savePageNumber(eventData: number){
    this.pageNumber = eventData;
  }

  saveTotalElements(eventData: number){
    this.totalElements = eventData;
  }
}
