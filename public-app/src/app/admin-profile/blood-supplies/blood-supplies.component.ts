import { Component, OnInit } from '@angular/core';
import { IBloodSupply } from '../model/BloodSupply';
import { AdminInfoService } from '../service/admin-info.service';

@Component({
  selector: 'app-blood-supplies',
  templateUrl: './blood-supplies.component.html',
  styleUrls: ['./blood-supplies.component.scss']
})
export class BloodSuppliesComponent implements OnInit {

  constructor(private adminInfoService: AdminInfoService) { }

  displayedColumns: string[] = ["bloodType", "quantity"]
  bloodSupplies: IBloodSupply[]; 
  ngOnInit(): void {
    this.adminInfoService.getBloodSupplies().subscribe(data=>{this.bloodSupplies = data;});
  }

}
