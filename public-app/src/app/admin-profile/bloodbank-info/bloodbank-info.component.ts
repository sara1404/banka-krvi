import { Component, OnInit } from '@angular/core';
import { IBloodBank } from '../model/BloodBank';
import { AdminInfoService } from '../service/admin-info.service';

@Component({
  selector: 'app-bloodbank-info',
  templateUrl: './bloodbank-info.component.html',
  styleUrls: ['./bloodbank-info.component.scss']
})
export class BloodbankInfoComponent implements OnInit {

  constructor(private adminInfoService: AdminInfoService) { }

  public isDisabled: boolean = true;
  public bloodBank: IBloodBank;
  ngOnInit(): void {
    this.adminInfoService.getBloodBank().subscribe(data=>{this.bloodBank = data;});
  }

  editClick()
  {
    this.isDisabled = false;
  }
  okClick()
  {
    this.isDisabled = true;
  }

}
