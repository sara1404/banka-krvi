import { Component, OnInit } from '@angular/core';
import { first, Observable } from 'rxjs';
import { IUser } from '../model/User';
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
  public bloodTypes: string[] = ['A_POSITIVE', 'A_NEGATIVE', 'B_POSITIVE', 'B_NEGATIVE', 'AB_POSITIVE', 'AB_NEGATIVE', 'O_POSITIVE', 'O_NEGATIVE'];

  public isDisabled: boolean = true;
  complete: boolean = true;
  showSave: boolean = false;
  public okName: boolean = true;
  public okSurname: boolean = true;
  public okEmail: boolean = true;
  public okJMBG: boolean = true;
  public okPassword: boolean = true;
  public okBloodType: boolean = true;

  ngOnInit(): void {
    this.adminInfoService.getUser(3).subscribe(data=>{this.user = data;});
  }

  validate(
    firstName: string,
    lastName: string,
    jmbg: string,
    email: string,
    password: string,
    bloodType: string):
    boolean{
    if(firstName == "" || lastName =="" || email=="" || password =="" || bloodType == "" || jmbg==""){
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
    if(password.length < 6){
      this.okPassword = false;
    }else{
      this.okPassword = true;
    }
    if(!this.bloodTypes.includes(bloodType)){
      this.okBloodType = false;
    }else{
      this.okBloodType = true;
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
  }
  okClick(
    firstName: string,
    lastName: string,
    jmbg: string,
    email: string,
    password: string,
    bloodType: string
  )
  {
    if(!this.validate(firstName, lastName, jmbg, email, password, bloodType)){
      return;
    }
    const updatedProfile: IUser = {
      id: this.user.id,
      userType: this.user.userType,
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      bloodType: bloodType,
      bloodBank:this.user.bloodBank,
      jmbg: Number(jmbg)
    };
    this.adminInfoService.editUser(updatedProfile).subscribe(data=>{this.user = data;});
    this.isDisabled = true;
  }

}

