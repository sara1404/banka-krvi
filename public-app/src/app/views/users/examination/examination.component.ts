import { ThisReceiver } from '@angular/compiler';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IAppointment } from 'src/app/admin-profile/model/Appointment';
import { IAppointmentAndInfo } from 'src/app/admin-profile/model/AppointmentAndInfo';
import { IAppointmentInfo } from 'src/app/model/AppointmentInfo';
import { IUser } from 'src/app/model/User';
import { IUserAppointment } from 'src/app/model/UserAppointment';
import { IUserSurvey } from 'src/app/model/UserSurvey';
import { AppointmentInfoService } from 'src/app/services/appointment-info-sevice';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-examination',
  templateUrl: './examination.component.html',
  styleUrls: ['./examination.component.scss']
})
export class ExaminationComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) private _data: any, private toastService: ToastService, private userService: UserService, private appointmentInfoService: AppointmentInfoService) { }

  userSurvey: IUserSurvey;
  displayedColumns: string[] = ['start', 'duration', 'startExamination', 'didntShowUp', 'unsuitable'];
  public result: Boolean
  start: boolean= false;
  userAppointments: IUserAppointment[]
  appointment: IUserAppointment
  ngOnInit(): void {
    this.userService.getSurveyForUser(this._data.user.id).subscribe(data=>{this.userSurvey = data;});
    this.userService.getAppointmentsForUser(this._data.user.id).subscribe(data=>{this.userAppointments = data;});
  }

  checkSurvey(): boolean{
    if(this.userSurvey?.weight >= 50 && this.userSurvey?.fluSymptoms == false && this.userSurvey?.skinIrritations == false
      && this.userSurvey?.abnormalBloodPressure == false && this.userSurvey?.tookAntibiotics == false 
      && this.userSurvey?.onPeriod == false && this.userSurvey?.dentistIntervention == false 
      && this.userSurvey?.piercingOrTattoo == false){
        return true;
      }
      return false;
  }

  startExamination(app: IUserAppointment){
    this.appointment = app;
    this.start = true;
  }
  didntShowUp(element: IUserAppointment){
    this.userService.addPenalPoints(this.userSurvey.userId).subscribe(data=>{this.result = data});
    this.userService.finishAppointment(element.id).subscribe(data => {this.result = data});
  }

  unsuitable(element: IUserAppointment){
    const info: IAppointmentInfo = {
      cuso4: null,
      hemoglobinometer: null,
      ta: null,
      tv: null,
      tt: null,
      hand: null,
      quantity: null,
      surveyAccepted: false,
      accepted: null,
      reason: null,
      examBloodType: "A_POSITIVE"
    };
    const appointmentAndInfo: IAppointmentAndInfo = {
      appointmentId: element.id,
      appointmentInfoDto: info
    }
    console.log(appointmentAndInfo)
    this.appointmentInfoService.createAppointmentInfo(appointmentAndInfo).subscribe(({
      next: (res) => {
        this.showSuccess()
      },
      error: (e) => {
        this.showError(e)
      },
    }));
    this.userService.finishAppointment(element.id).subscribe(data => {this.result = data});
  }
  showError(e) {
    this.toastService.showError("Error");
  }

  showSuccess() {
    this.toastService.showSuccess('Successfully saved info.');

  }

}
