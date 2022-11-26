import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IUser } from 'src/app/model/User';
import { IUserSurvey } from 'src/app/model/UserSurvey';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-examination',
  templateUrl: './examination.component.html',
  styleUrls: ['./examination.component.scss']
})
export class ExaminationComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) private _data: any, private userService: UserService) { }

  userSurvey: IUserSurvey;
  ngOnInit(): void {
    console.log(this._data.user.id)
    this.userService.getSurveyForUser(this._data.user.id).subscribe(data=>{this.userSurvey = data;});
  }

}
