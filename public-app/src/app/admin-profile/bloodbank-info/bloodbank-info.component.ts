import { Component, OnInit } from '@angular/core';
import { IAddress } from '../model/Address';
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
  okClick(
    name: string,
    street: string,
    number: string,
    city: string,
    zipcode: string,
    country: string,
    longitude: string,
    latitude: string,
    description: string,
    averageGrade: string
  )
  {
    const updatedAddress: IAddress={
      id: this.bloodBank.address.id,
      street: street,
      number: Number(number),
      city: city,
      zipcode: Number(zipcode),
      country: country,
      longitude: Number(longitude),
      latitude: Number(latitude)
    }
    const updatedBloodBank: IBloodBank = {
      id: this.bloodBank.id,
      name: name,
      address: updatedAddress,
      description: description,
      averageGrade: Number(averageGrade)
    }
    this.adminInfoService.editBloodBank(updatedBloodBank).subscribe(data=>{this.bloodBank = data;});
    this.isDisabled = true;
  }

}
