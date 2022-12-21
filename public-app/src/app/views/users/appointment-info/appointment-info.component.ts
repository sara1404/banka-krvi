import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {
  AbstractControl,
  Form,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { TitleStrategy } from '@angular/router';
import { isNumber } from '@ng-bootstrap/ng-bootstrap/util/util';
import { IAppointmentAndInfo } from 'src/app/admin-profile/model/AppointmentAndInfo';
import { IAppointmentInfo } from 'src/app/model/AppointmentInfo';
import { IEquipment } from 'src/app/model/Equipment';
import { IUser } from 'src/app/model/User';
import { IUserAppointment } from 'src/app/model/UserAppointment';
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

  @Input() appointment: IUserAppointment;
  result: Boolean
  acceptedClick: boolean = true;
  handSelect: string
  @Output() newItemEvent = new EventEmitter<boolean>();

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
      //startBlood: new FormControl('', [Validators.required]),
      //endBlood: new FormControl('', [Validators.required]),
      reason: new FormControl('', [Validators.required]),
      needle: new FormControl('', [Validators.required]),
      bandage: new FormControl('', [Validators.required]),
      bag: new FormControl('', [Validators.required]),
      accepted: new FormControl('', [Validators.required])
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
  get needle() {
    return this.appInfoForm.get('needle');
  }
  get bandage() {
    return this.appInfoForm.get('bandage');
  }
  get bag() {
    return this.appInfoForm.get('bag');
  }
  /*
  get startBlood() {
    return this.appInfoForm.get('startBlood');
  }
  get endBlood() {
    return this.appInfoForm.get('endBlood');
  }
  */

  changeSelectedAcceptance(){
    console.log('menja')
    console.log(this.acceptedClick)
  }
  changeHand(evt: any){
    console.log('stampa value')
    console.log(evt.value)
    this.appInfoForm.value.hand = evt.value
    this.handSelect = evt.value
    console.log('stampa polje')
    console.log(this.appInfoForm.value.hand)
  }
  finish(){
    /*if(this.appInfoForm.valid == false) {
      console.log('nece')
      return;
    }*/
    console.log(this.acceptedClick)
    const info: IAppointmentInfo = {
      cuso4: this.appInfoForm.value.cuso4,
      hemoglobinometer: this.appInfoForm.value.hemoglobinometer,
      ta: this.appInfoForm.value.ta,
      tv: this.appInfoForm.value.tv,
      tt: this.appInfoForm.value.tt,
      hand: this.handSelect, //this.appInfoForm.value.hand,
      quantity: this.appInfoForm.value.quantity,
      surveyAccepted: true,
      accepted: this.acceptedClick,
      reason: this.appInfoForm.value.reason
    };
    const appointmentAndInfo: IAppointmentAndInfo = {
      appointmentId: this.appointment.id,
      appointmentInfoDto: info
    }
    //console.log(info)
    this.appointmentInfoService.createAppointmentInfo(appointmentAndInfo).subscribe(({
      next: (res) => {
        this.showSuccess()
      },
      error: (e) => {
        this.showError(e)
      },
    }));

    const needles: IEquipment = {
      equipmentType: "NEEDLE",
      quantity: this.appInfoForm.value.needle,
      bloodBank: this.appointment.bloodBank
    }
    const bandages: IEquipment = {
      equipmentType: "BANDAGE",
      quantity: this.appInfoForm.value.bandage,
      bloodBank: this.appointment.bloodBank
    }
    const bags: IEquipment = {
      equipmentType: "BAG",
      quantity: this.appInfoForm.value.bag,
      bloodBank: this.appointment.bloodBank
    }
    this.userService.finishAppointment(this.appointment.id).subscribe(data => {this.result = data});
    this.appointmentInfoService.usedEquipment(needles).subscribe();
    this.appointmentInfoService.usedEquipment(bandages).subscribe();
    this.appointmentInfoService.usedEquipment(bags).subscribe();
    this.newItemEvent.emit(true);
  }
  showError(e) {
    this.toastService.showError("Error");
  }

  showSuccess() {
    this.toastService.showSuccess('Successfully saved info.');

  }
}
