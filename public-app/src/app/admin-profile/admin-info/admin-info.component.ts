import { Component, OnInit } from '@angular/core';
import { first, Observable } from 'rxjs';
import { IUser } from '../model/User';
import { IAddress } from '../model/Address';
import { AdminInfoService } from '../service/admin-info.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-admin-info',
  templateUrl: './admin-info.component.html',
  styleUrls: ['./admin-info.component.scss']
})
export class AdminInfoComponent implements OnInit {

  constructor(private adminInfoService: AdminInfoService) { }

  user: IUser;
  public bloodTypes: string[] = ['A_POSITIVE', 'A_NEGATIVE', 'B_POSITIVE', 'B_NEGATIVE', 'AB_POSITIVE', 'AB_NEGATIVE', 
  'O_POSITIVE', 'O_NEGATIVE'];
  gender: number = 0;

  public isDisabled: boolean = true;
  complete: boolean = true;
  showSave: boolean = false;
  showEdit: boolean = true;
  public okName: boolean = true;
  public okSurname: boolean = true;
  public okEmail: boolean = true;
  public okJMBG: boolean = true;
  public okPassword: boolean = true;
  public okBloodType: boolean = true;
  okStreet: boolean = true;
  okNumber: boolean = true;
  okCity: boolean = true;
  okZipcode: boolean = true;
  okCountry: boolean = true;
  updated: boolean = false;

  ngOnInit(): void {
    this.adminInfoService.getUser(3).subscribe(data=>{this.user = data;});

  }

  validate(
    firstName: string,
    lastName: string,
    jmbg: string,
    email: string,
    street: string,
    number: string,
    city: string,
    zipcode: string,
    country: string):
    boolean{
    if(firstName == "" || lastName =="" || email=="" || jmbg==""
    || street=="" || number=="" ||city=="" || zipcode == "" || country == ""){
      this.complete = false;
    }else{
      this.complete = true;
    }
    if(!email.match(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)){
      this.okEmail = false;
    }else{
      this.okEmail = true;
    }
    if(firstName.substring(0,1) != firstName.substring(0,1).toUpperCase()){
      this.okName = false;
    }else{
      this.okName = true;
    }
    if(lastName.substring(0,1) != lastName.substring(0,1).toUpperCase()){
      this.okSurname = false;
    }else{
      this.okSurname = true;
    }
    if(!Number(jmbg) || jmbg.length < 13){
      this.okJMBG = false;
    }else{
      this.okJMBG = true;
    }
    if(!this.complete || !this.okEmail || !this.okName || !this.okSurname || !this.okJMBG || !this.okBloodType){
      return false;
    }
    return true;
  }
  editClick()
  {
    this.isDisabled = false;
    this.showSave = true;
    this.showEdit = false;
    this.updated = false;
  }
  okClick(
    firstName: string,
    lastName: string,
    jmbg: string,
    email: string,
    phoneNumber: string,
    street: string,
    number: string,
    city: string,
    zipcode: string,
    country: string
  )
  {
    if(!this.validate(firstName, lastName, jmbg, email, street, number, city, zipcode, country)){
      return;
    }
    const updatedAddress: IAddress={
      id: this.user.address.id,
      street: street,
      number: Number(number),
      city: city,
      zipcode: Number(zipcode),
      country: country,
      longitude: this.user.address.longitude,
      latitude: this.user.address.latitude
    }
    const updatedProfile: IUser = {
      id: this.user.id,
      userType: this.user.userType,
      firstName: firstName,
      lastName: lastName,
      email: email,
      bloodType: this.user.bloodType,
      jmbg: Number(jmbg),
      phoneNumber: Number(phoneNumber),
      address: updatedAddress,
      workplaceName: this.user.workplaceName,
      jobTitle: this.user.jobTitle,
      gender: this.gender,
      bloodBank: this.user.bloodBank,
      password: this.user.password
    };
    this.adminInfoService.editUser(updatedProfile).subscribe(data=>{this.user = data;});
    this.isDisabled = true;
    this.showSave = false;
    this.showEdit = true;
    this.updated = true;
  }
  
  onGenderChange(entry): void {
    this.gender = entry;
  }
  

}

