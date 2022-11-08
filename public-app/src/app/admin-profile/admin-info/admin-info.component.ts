import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IUser } from '../model/User';
import { AdminInfoService } from '../service/admin-info.service';

@Component({
  selector: 'app-admin-info',
  templateUrl: './admin-info.component.html',
  styleUrls: ['./admin-info.component.scss']
})
export class AdminInfoComponent implements OnInit {

  constructor(private adminInfoService: AdminInfoService) { }

  user: IUser;
  public isDisabled: boolean = true;
  ngOnInit(): void {
    this.adminInfoService.getUser(3).subscribe(data=>{this.user = data;});
  }
  editClick()
  {
    this.isDisabled = false;
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
    const updatedProfile: IUser = {
      id: this.user.id,
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
