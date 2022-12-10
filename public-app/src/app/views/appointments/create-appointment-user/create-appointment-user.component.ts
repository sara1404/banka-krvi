import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../../services/appointment.service';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastService } from '../../../services/toast.service';
import { BloodBankService } from 'src/app/services/blood-bank.service';
import { MatTableDataSource } from '@angular/material/table';
import { IBloodBank } from 'src/app/model/BloodBankk';
import { SelectionModel } from '@angular/cdk/collections';


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
  public surveyForm: FormGroup;
  constructor(private fb: FormBuilder,private appointmentService: AppointmentService,private bloodBankService: BloodBankService, private toastService: ToastService) {
    this.surveyForm = fb.group({
      weight: [0, [Validators.required, Validators.min(0), Validators.max(250)]],
      fluSymptoms: [true, [Validators.required]],
      skinIrritations: [true, [Validators.required]],
      abnormalBloodPressure: [true, [Validators.required]],
      tookAntibiotics: [true, [Validators.required]],
      onPeriod: [true, [Validators.required]],
      dentistIntervention: [true, [Validators.required]],
      piercingOrTattoo: [true, [Validators.required]],
    });
   }
  ngOnInit(){

  } 

  startTimeForm = new FormGroup({
    startTime : new FormControl(null, [Validators.required]),
  })
  bloodBanks = new MatTableDataSource<IBloodBank>()
  selection = new SelectionModel<IBloodBank>(false);
  displayedColumns: string[] = ["name", "city", "averageGrade"]
  showSpinner : boolean = false

  recommendBloodBanks() {
    this.showSpinner = true
    console.log(this.startTimeForm.getRawValue());
    this.bloodBankService.getBloodBanksWithFreeTimeSlots(this.startTimeForm.controls.startTime.value).subscribe({
      next: (res) => {
        this.showSpinner = false
        this.bloodBanks = new MatTableDataSource(res);
        console.log(res)
      },
      error: (e) => {
        console.log("error")
      },
    }
    )
  }

  deleteBloodBanks(){
    this.bloodBanks = new MatTableDataSource([]);
  }

  showQuestions(){
    console.log(this.selection.selected)
  }

  scheduleAppointment(){
    
  }
}

