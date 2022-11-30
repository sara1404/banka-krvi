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

  constructor(fb: FormBuilder, private userService: UserService, private toastService: ToastService) {
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
      endBlood: new FormControl('', [Validators.required])
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
}
