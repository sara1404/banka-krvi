import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastService } from '../../services/toast.service';
import {IWorkingHours} from '../../model/WorkingHours'
import { BloodBankService } from 'src/app/services/blood-bank.service';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

function timeValidator(c: FormControl) {
  const startTime = new Date(CreateAppointmentComponent.workingHours.startTime)
  const endTime = new Date(CreateAppointmentComponent.workingHours.endTime)
  const chosenTime = new Date(c.value)
  if(chosenTime.getHours() < startTime.getHours() || (chosenTime.getHours() == startTime.getHours() && chosenTime.getMinutes() < startTime.getMinutes())) {
    return {dateValidator: {valid: false}};
  }
  else if (chosenTime.getHours() > endTime.getHours() || (chosenTime.getHours() == endTime.getHours() && chosenTime.getMinutes() >= endTime.getMinutes())){
    return {dateValidator: {valid: false}};
  }
  else {
      return null
  }
}


@Component({
  selector: 'app-users',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.scss']
})
export class CreateAppointmentComponent implements OnInit {

  constructor(private appointmentService: AppointmentService,private bloodBankService: BloodBankService, private toastService: ToastService) { }
  public static workingHours : IWorkingHours = {startTime: new Date(), endTime : new Date()}
  getWorkingHours() : IWorkingHours {
    return CreateAppointmentComponent.workingHours
  }
  ngOnInit(){
    this.bloodBankService.getBloodBankWorkingHours().subscribe({
      next: (data) => {
        CreateAppointmentComponent.workingHours = data
        console.log(data)
      },
      error: (e) => {
        console.log("error")
      },
    })

  }
  createAppointmentForm = new FormGroup({
    startTime : new FormControl(null, [Validators.required, timeValidator]),
    duration: new FormControl(null, [Validators.required]),
    bloodBank: new FormControl(null),
    id: new FormControl(null)
  })
 
  matcher = new MyErrorStateMatcher();

  saveClick(e : Event){
    e.preventDefault();
    this.appointmentService.createAppointment(this.createAppointmentForm.getRawValue()).subscribe({
      next: (res) => {
        this.toastService.showSuccess("Successfuly created apointment")
      },
      error: (e) => {
        console.log("error")
        this.toastService.showError("Error")
      },
    })
  }
}

