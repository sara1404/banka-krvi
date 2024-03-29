import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IAppointmentAndInfo } from 'src/app/admin-profile/model/AppointmentAndInfo';
import { IAppointmentInfo } from 'src/app/model/AppointmentInfo';
import { IUser } from 'src/app/model/User';
import { IUserAppointment } from 'src/app/model/UserAppointment';
import { IUserSurvey } from 'src/app/model/UserSurvey';
import { AppointmentInfoService } from 'src/app/services/appointment-info-sevice';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-clicked-appointment',
  templateUrl: './clicked-appointment.component.html',
  styleUrls: ['./clicked-appointment.component.scss']
})
export class ClickedAppointmentComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ClickedAppointmentComponent>, @Inject(MAT_DIALOG_DATA) private _data: any, private toastService: ToastService, private userService: UserService, private appointmentInfoService: AppointmentInfoService) {

   }

  userSurvey: IUserSurvey;
  //displayedColumns: string[] = ['start', 'duration', 'startExamination', 'didntShowUp', 'unsuitable'];
  public result: Boolean
  start: boolean= false;
  userAppointments: IUserAppointment[]
  appointment: IUserAppointment
  noAppointments: boolean = false;
  user: IUser
  ngOnInit(): void {
    console.log(this._data.user)
    this.userService.getSurveyForUser(this._data.user.id).subscribe(data=>{
      this.userSurvey = data;
      this.appointment = this._data.appointment;
      this.user = this._data.user;
      console.log(this.appointment)
    });

  }

  finishedAppointment():boolean{
    if(this._data.appointment.finished == true) return true
    return false
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
    this.dialogRef.close();
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
      reason: null
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
    //this.userService.finishAppointment(element.id).subscribe(data => {this.result = data});
    this.dialogRef.close();
  }
  showError(e) {
    this.toastService.showError("Error");
  }

  showSuccess() {
    this.toastService.showSuccess('Successfully saved info.');

  }

  close(close: boolean){
    if(close == true) this.dialogRef.close();
  }

}
