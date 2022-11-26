import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastService } from '../../services/toast.service';
import {IWorkingHours} from '../../model/WorkingHours'


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

function timeValidator(c: FormControl) {
  const startTime = new Date(CreateAppointmentComponent.workingHours.startTime);
  const endTime = new Date(CreateAppointmentComponent.workingHours.endTime)
  const chosenTime = new Date(c.value)
  if(chosenTime.getTime < startTime.getTime) {
    return {dateValidator: {valid: false}};
  }
  else if (chosenTime.getTime > endTime.getTime){
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

  constructor(private appointmentService: AppointmentService, private toastService: ToastService) { }

  ngOnInit(){}
  public static workingHours : IWorkingHours
  createAppointmentForm = new FormGroup({
    startTime : new FormControl({value : null}, [Validators.required, timeValidator]),
    duration: new FormControl({value : null}, [Validators.required])
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

