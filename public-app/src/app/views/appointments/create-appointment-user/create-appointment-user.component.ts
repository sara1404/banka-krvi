import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../../services/appointment.service';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastService } from '../../../services/toast.service';
import { BloodBankService } from 'src/app/services/blood-bank.service';
import { MatTableDataSource } from '@angular/material/table';
import { IBloodBank } from 'src/app/model/BloodBankk';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-users',
  templateUrl: './create-appointment-user.component.html',
  styleUrls: ['./create-appointment-user.component.scss']
})
export class CreateAppointmentUserComponent implements OnInit {

  constructor(private appointmentService: AppointmentService,private bloodBankService: BloodBankService, private toastService: ToastService) { }
  ngOnInit(){

  } 

  startTimeForm = new FormGroup({
    startTime : new FormControl(null, [Validators.required]),
  })
  bloodBanks = new MatTableDataSource<IBloodBank>()
  displayedColumns: string[] = ["name", "city", "averageGrade"]
 

  recommendBloodBanks() {
    console.log(this.startTimeForm.getRawValue());
    this.bloodBankService.getBloodBanksWithFreeTimeSlots(this.startTimeForm.controls.startTime.value).subscribe({
      next: (res) => {
        this.bloodBanks = new MatTableDataSource(res);
        console.log(res)
      },
      error: (e) => {
        console.log("error")
      },
    }
    )
  }
}

