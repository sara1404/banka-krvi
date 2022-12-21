import { throwDialogContentAlreadyAttachedError } from '@angular/cdk/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-year-view',
  templateUrl: './year-view.component.html',
  styleUrls: ['./year-view.component.scss']
})
export class YearViewComponent implements OnInit {

  selected: Date

  today = new Date()
  sixMonthsAgo = new Date()

  
  yearViewForm = new FormGroup({
    date : new FormControl('', [Validators.required]),
  })

  constructor(public dialogRef: MatDialogRef<YearViewComponent>, @Inject(MAT_DIALOG_DATA) private _data: any) { 
    this.sixMonthsAgo.setMonth(this.today.getMonth() - 12);
    dialogRef.beforeClosed().subscribe(() => dialogRef.close(this.selected));
  }

  ngOnInit(): void {
  }


  openDatePicker(dp) {
    dp.open();
  }

  closeDatePicker(eventData: any, dp?:any) {
    // get month and year from eventData and close datepicker, thus not allowing user to select date
    this.selected = eventData
    dp.close();  
    this.dialogRef.close()  
  }
}
