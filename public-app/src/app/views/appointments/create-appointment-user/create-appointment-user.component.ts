import { Component, OnInit, Input} from '@angular/core';
import { AppointmentService } from '../../../services/appointment.service';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastService } from '../../../services/toast.service';
import { BloodBankService } from 'src/app/services/blood-bank.service';
import { MatTableDataSource } from '@angular/material/table';
import { IAppointment } from 'src/app/model/Appointment';
import { IBloodBank } from 'src/app/model/BloodBankk';
import { SelectionModel } from '@angular/cdk/collections';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateAppointmentDialogComponent } from './create-appointment-dialog/create-app-dialog.component';


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
  constructor(public dialog: MatDialog, private fb: FormBuilder,private appointmentService: AppointmentService,private bloodBankService: BloodBankService, private toastService: ToastService, private userService: UserService, private router: Router) {
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
  matcher = new MyErrorStateMatcher();
  isSelected(){
    return this.selection.selected[0] != null || this.createdAppointment != false
  }

  appointments = new MatTableDataSource<IAppointment>([])
  selection = new SelectionModel<IBloodBank>(false);
  displayedColumns: string[] = ["name", "city", "averageGrade"]
  showSpinner : boolean = false
  pageNumber: number = 0
  totalElements: number = 0
  sortDirection: string = "ASC"
  createdAppointment : boolean = false
  appointment : IAppointment 
  canSchedule : Boolean = true

  recommendBloodBanks() {
    this.appointmentService.canUserScheduleAppointment(this.startTimeForm.controls.startTime.value).subscribe({
      next : (data) => {
        this.canSchedule = data
        if (this.canSchedule == false){
          this.toastService.showError("Sorry, you cannot schedule an appointment");
        }
      }
    })
    this.showSpinner = true
    console.log(this.startTimeForm.getRawValue());
    this.appointmentService.getBloodBanksWithFreeTimeSlots(this.startTimeForm.controls.startTime.value, this.pageNumber, this.sortDirection).subscribe({
      next: (res) => {
        console.log(res)
        this.showSpinner = false
        this.appointments = new MatTableDataSource(res.content);
        this.totalElements = res.totalElements;
      },
      error: (e) => {
        console.log("error")
      },
    }
    )
  }

  onPageChanged(e : any){
    this.pageNumber = e.pageIndex;
    console.log(this.pageNumber)
    this.appointmentService.getBloodBanksWithFreeTimeSlots(this.startTimeForm.controls.startTime.value, this.pageNumber, this.sortDirection).subscribe({
      next: (res) => {
        console.log(res)
        this.showSpinner = false
        this.appointments = new MatTableDataSource(res.content);
        this.totalElements = res.totalElements;
      },
      error: (e) => {
        console.log("error")
      },
    }
    )
  }

  sort(e: any) {
    this.sortDirection = e.value;
    this.appointmentService.getBloodBanksWithFreeTimeSlots(this.startTimeForm.controls.startTime.value, this.pageNumber, this.sortDirection).subscribe({
      next: (res) => {
        console.log(res)
        this.showSpinner = false
        this.appointments = new MatTableDataSource(res.content);
        this.totalElements = res.totalElements;
      },
      error: (e) => {
        console.log("error")
      },
    }
    )
  }

  deleteBloodBanks(){
    this.appointments = new MatTableDataSource([]);
    this.selection.selected[0] = null;
  }

  scheduleAppointment(){
    if (this.canSchedule == false){
      this.toastService.showError("Sorry, you cannot schedule an appointment");
      console.log("sorry");
      return;
    }
    if(this.surveyForm.valid && this.canSchedule) {
      this.userService.sendSurvey(this.surveyForm.value).subscribe(
        (res) => {
          this.toastService.showSuccess("Successfully sent survey!");
        },
        (err) => {
          this.toastService.showError("Error occured while sending survey, status code: " + err.status);
        }
      );

      if (!this.createdAppointment) {
        this.appointmentService.scheduleAppointment(this.selection.selected[0]).subscribe(
          (res) => {
            this.toastService.showSuccess("Successfully scheduled appointment!");
            this.router.navigate(['/']);
          },
          (err) => {
            this.toastService.showError("Error occured while creating an appointment, status code: " + err.status);
          }
        );
      } else {
        this.appointmentService.userCreates(this.appointment).subscribe(
          (res) => {
            this.toastService.showSuccess("Successfully created appointment!");
            this.router.navigate(['/']);
          },
          (err) => {
            this.toastService.showError("Error occured while creating an appointment, status code: " + err.status);
          }
        );
      }
    }
  }

  openDialog() {
    let dialogRef = this.dialog.open(CreateAppointmentDialogComponent, {
      disableClose: true,
    });
    dialogRef.componentInstance.startTime = this.startTimeForm.controls.startTime.value
    dialogRef.afterClosed().subscribe(result => {
      if (result == 'canceled') return
      else {
        this.createdAppointment = true
        this.appointment = result.data
      }
    });
  }
}

