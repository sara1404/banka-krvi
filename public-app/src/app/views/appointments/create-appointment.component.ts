import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastService } from '../../services/toast.service';
import {IWorkingHours} from '../../model/WorkingHours'
import { BloodBankService } from 'src/app/services/blood-bank.service';
import { IUser } from 'src/app/model/User';


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
    id: new FormControl(null),
    nurse: new FormControl([])
  })

  medicalStaffForm = new FormGroup({
    medicalStaff: new FormControl([]),
  });

  medicalStaff: IUser[] = [];
  showSpinner: boolean = false;
 
  matcher = new MyErrorStateMatcher();

  findNurseById(id:number) : IUser {
    var nurse = null
    this.medicalStaff.forEach(i => {
      if (i.id == id) nurse = i
    });
    return nurse
  }

  recommendMedicalStaff(){
    this.medicalStaff = []
    this.createAppointmentForm.controls.nurse.setValue([])
    this.showSpinner = true;
    this.appointmentService.recommendMedicalStaff(this.createAppointmentForm.controls.startTime.value, this.createAppointmentForm.controls.duration.value).subscribe({
      next: (res) => {
        this.showSpinner = false;
        this.medicalStaff = res
        console.log(res);
      },
      error: (e) => {
        console.log("error")
        this.toastService.showError("Error")
      },
    })
  }

   isSelected(){
    return this.createAppointmentForm.controls.nurse.value[0] != null
  }

  saveClick(e : Event){
    var nurse = this.findNurseById(this.createAppointmentForm.controls.nurse.value[0])
    e.preventDefault();
    this.appointmentService.createAppointment({id: null, bloodBank: this.createAppointmentForm.controls.bloodBank.value,startTime: this.createAppointmentForm.controls.startTime.value, duration: this.createAppointmentForm.controls.duration.value, nurse: nurse}).subscribe({
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

