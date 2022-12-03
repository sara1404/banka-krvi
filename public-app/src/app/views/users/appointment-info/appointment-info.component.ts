import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  Form,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { isNumber } from '@ng-bootstrap/ng-bootstrap/util/util';
import { IAppointmentInfo } from 'src/app/model/AppointmentInfo';
import { IUser } from 'src/app/model/User';
import { AppointmentInfoService } from 'src/app/services/appointment-info-sevice';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';
@Component({
  selector: 'app-appointment-info',
  templateUrl: './appointment-info.component.html',
  styleUrls: ['./appointment-info.component.scss']
})
export class AppointmentInfoComponent implements OnInit {
  appInfoForm: FormGroup;
  user: IAppointmentInfo;

  constructor(fb: FormBuilder, private userService: UserService, private toastService: ToastService, private appointmentInfoService: AppointmentInfoService) {
    this.appInfoForm = fb.group({
      cuso4: new FormControl('', [Validators.required]),
      hemoglobinometer: new FormControl('', [Validators.required]),
      ta: new FormControl('', [Validators.required]),
      tt: new FormControl('', [Validators.required]),
      tv: new FormControl('', [Validators.required]),
      hand: new FormControl('', [Validators.required]),
      examBloodType: new FormControl('', [Validators.required]),
      quantity: new FormControl('', [Validators.required]),
      startBlood: new FormControl('', [Validators.required]),
      endBlood: new FormControl('', [Validators.required]),
      reason: new FormControl('', [Validators.required])
    });
   }

  ngOnInit(): void {
  }



  get cuso4() {
    return this.appInfoForm.get('cuso4');
  }
  get hemoglobinometer() {
    return this.appInfoForm.get('hemoglobinometer');
  }
  get ta() {
    return this.appInfoForm.get('ta');
  }
  get tt() {
    return this.appInfoForm.get('tt');
  }
  get tv() {
    return this.appInfoForm.get('tv');
  }
  get hand() {
    return this.appInfoForm.get('hand');
  }
  get examBloodType() {
    return this.appInfoForm.get('examBloodType');
  }
  get quantity() {
    return this.appInfoForm.get('quantity');
  }
  get startBlood() {
    return this.appInfoForm.get('startBlood');
  }
  get endBlood() {
    return this.appInfoForm.get('endBlood');
  }

  finish(){
    /*if(this.appInfoForm.valid == false) {
      console.log('nece')
      return;
    }*/
    const info: IAppointmentInfo = {
      cuso4: this.appInfoForm.value.cuso4,
      hemoglobinometer: this.appInfoForm.value.hemoglobinometer,
      ta: this.appInfoForm.value.ta,
      tv: this.appInfoForm.value.tv,
      tt: this.appInfoForm.value.tt,
      hand: this.appInfoForm.value.hand,
      quantity: this.appInfoForm.value.quantity,
      startBlood: null,
      endBlood: null,
      surveyAccepted: true,
      accepted: this.appInfoForm.value.accepted,
      reason: this.appInfoForm.value.reason,
      examBloodType: this.appInfoForm.value.examBloodType
    };
    //console.log(info)
    this.appointmentInfoService.createAppointmentInfo(info).subscribe(({
      next: (res) => {
        this.showSuccess()
      },
      error: (e) => {
        this.showError(e)
      },
    }));
    /*
    this.info.cuso4 = Number(this.appInfoForm.get('cuso4'));
    this.info.hemoglobinometer = this.appInfoForm.value.hemoglobinometer
    this.info.ta = this.appInfoForm.value.ta
    this.info.tv = this.appInfoForm.value.tv
    this.info.tt = this.appInfoForm.value.tt
    this.info.hand = this.appInfoForm.value.hand
    this.info.quantity = this.appInfoForm.value.quantity
    this.info.startBlood = null //this.appInfoForm.value.startBlood
    this.info.endBlood = null//this.appInfoForm.value.endBlood
    this.info.surveyAccepted = true
    this.info.accepted = this.appInfoForm.value.accepted
    this.info.reason = this.appInfoForm.value.reason
    console.log(this.info)
    this.appointmentInfoService.createAppointmentInfo(this.info).subscribe(({
      next: (res) => {
        this.showSuccess()
      },
      error: (e) => {
        this.showError(e)
      },
    }));*/
  }
  showError(e) {
    this.toastService.showError("Error");
  }

  showSuccess() {
    this.toastService.showSuccess('Successfully saved info.');

  }
}
