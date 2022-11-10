import { Component, OnInit } from '@angular/core';
import { zip } from 'rxjs';
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
  public bloodBank: IBloodBank;

  public isDisabled: boolean = true;
  complete: boolean = true;
  showSave: boolean = false;
  okName: boolean = true;
  okStreet: boolean = true;
  okNumber: boolean = true;
  okCity: boolean = true;
  okZipcode: boolean = true;
  okCountry: boolean = true;
  okLongitude: boolean = true;
  okLatitude: boolean = true;
  okDescription: boolean = true;
  okAverageGrade: boolean = true;
  
  ngOnInit(): void {
    this.adminInfoService.getBloodBank().subscribe(data=>{this.bloodBank = data;});
  }

  validate(
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
  ):boolean{
    if(name==""|| street=="" || number=="" ||city=="" || zipcode == "" || country == "" || 
    longitude == "" || latitude == "" || description == "" || averageGrade == ""){
      this.complete = false;
    }else{
      this.complete = true;
    }
    if(name.substring(0,1) != name.substring(0,1).toUpperCase()){
      this.okName = false;
    }else{
      this.okName = true;
    }
    if(street.substring(0,1) != street.substring(0,1).toUpperCase()){
      this.okStreet = false;
    }else{
      this.okStreet = true;
    }
    if(!Number(number)){
      this.okNumber = false;
    }else{
      this.okNumber = true;
    }
    if(city.substring(0,1) != city.substring(0,1).toUpperCase()){
      this.okCity = false;
    }else{
      this.okCity = true;
    }
    if(!Number(zipcode) || zipcode.length < 5){
      this.okZipcode = false;
    }else{
      this.okZipcode = true;
    }
    if(country.substring(0,1) != country.substring(0,1).toUpperCase()){
      this.okCountry = false;
    }else{
      this.okCountry = true;
    }
    if(!Number(longitude)){
      this.okLongitude = false;
    }else{
      this.okLongitude = true;
    }
    if(!Number(latitude)){
      this.okLatitude = false;
    }else{
      this.okLatitude= true;
    }
    if(!this.complete || !this.okName || !this.okCity || !this.okNumber || !this.okZipcode || !this.okCountry 
      || !this.okLongitude || !this.okLatitude || !this.okDescription || !this.okAverageGrade){
      return false;
    }
    return true;
  }

  editClick()
  {
    this.isDisabled = false;
    this.showSave = true;
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
    if(!this.validate(name, street, number, city, zipcode, country, longitude, latitude, description, averageGrade)){
      return;
    }
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
