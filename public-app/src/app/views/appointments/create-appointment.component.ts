import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastService } from '../../services/toast.service';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
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

  createAppointmentForm = new FormGroup({
    startTime : new FormControl({value : null}, [Validators.required]),
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
