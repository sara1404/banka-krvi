import { Component, EventEmitter, Inject, Input, OnInit, Optional, Output } from '@angular/core';
import { AppointmentService } from '../../../../services/appointment.service';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IUser } from 'src/app/model/User';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-create-appointment-dialog',
  templateUrl: './create-app-dialog.component.html',
  styleUrls: ['./create-app-dialog.component.scss']
})
export class CreateAppointmentDialogComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<CreateAppointmentDialogComponent>,
              @Optional() @Inject(MAT_DIALOG_DATA) public data: any, 
              private appointmentService: AppointmentService) {}

  ngOnInit(){

  } 

  public startTime : any
  public durationForm = new FormGroup({
    duration : new FormControl(null, [Validators.required]),
  })
  matcher = new MyErrorStateMatcher();
  isSelected(){
    return this.selection.selected[0] != null
  }

  bloodBanks = new MatTableDataSource<IUser>([])
  selection = new SelectionModel<IUser>(false);
  displayedColumns: string[] = ["name", "city", "averageGrade"]
  showSpinner : boolean = false
  pageNumber: number = 0
  totalElements: number = 0
  sortDirection: string = "ASC"

  recommendBloodBanks() {
    this.showSpinner = true
    this.appointmentService.getBloodBanksWithAvailableMedicalStaff(this.startTime, parseInt(this.durationForm.controls.duration.value), this.pageNumber, this.sortDirection).subscribe({
      next: (res) => {
        console.log(res)
        this.showSpinner = false
        this.bloodBanks = new MatTableDataSource(res.content);
        this.totalElements = res.totalElements
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
    this.appointmentService.getBloodBanksWithAvailableMedicalStaff(this.startTime, parseFloat(this.durationForm.controls.duration.value), this.pageNumber, this.sortDirection).subscribe({
      next: (res) => {
        this.bloodBanks = new MatTableDataSource(res.content);
        this.totalElements = res.totalElements
      },
      error: (e) => {
        console.log("error")
      },
    }
    )
  }

  sort(e: any) {
    this.sortDirection = e.value;
    this.appointmentService.getBloodBanksWithAvailableMedicalStaff(this.startTime, parseFloat(this.durationForm.controls.duration.value), this.pageNumber, this.sortDirection).subscribe({
      next: (res) => {
        this.bloodBanks = new MatTableDataSource(res.content);
        this.totalElements = res.totalElements
      },
      error: (e) => {
        console.log("error")
      },
    }
    )
  }

  deleteBloodBanks(){
    this.totalElements = 0
    this.bloodBanks = new MatTableDataSource([]);
    this.selection.selected[0] = null;
  }

  onClose(){
    this.dialogRef.close({event: 'close', data: {startTime: this.startTime, duration: parseFloat(this.durationForm.controls.duration.value), nurse: this.selection.selected[0], bloodBank: this.selection.selected[0].bloodBank}});
    }
  }


