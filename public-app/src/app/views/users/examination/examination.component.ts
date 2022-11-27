import { ThisReceiver } from '@angular/compiler';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IUser } from 'src/app/model/User';
import { IUserAppointment } from 'src/app/model/UserAppointment';
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
  displayedColumns: string[] = ['start', 'end', 'startExamination', 'didntShowUp', 'unsuitable'];
  userAppointments: IUserAppointment[]
  ngOnInit(): void {
    console.log(this._data.user.id)
    this.userService.getSurveyForUser(this._data.user.id).subscribe(data=>{this.userSurvey = data;});
    this.userService.getAppointmentsForUser(this._data.user.id).subscribe(data=>{this.userAppointments = data;});
  }

  checkSurvey(): boolean{
    console.log(this.userAppointments)
    if(this.userSurvey?.weight >= 50 && this.userSurvey?.fluSymptoms == false && this.userSurvey?.skinIrritations == false
      && this.userSurvey?.abnormalBloodPressure == false && this.userSurvey?.tookAntibiotics == false 
      && this.userSurvey?.onPeriod == false && this.userSurvey?.dentistIntervention == false 
      && this.userSurvey?.piercingOrTattoo == false){
        return true;
      }
      return false;
  }

  startExamination(){

  }
  didntShowUp(){

  }

  unsuitable(){

  }

}
