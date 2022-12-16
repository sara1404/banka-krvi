import { Component, Input, OnInit, Inject } from '@angular/core';
import { IUser } from 'src/app/model/User';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { ExaminationComponent } from '../examination/examination.component';

export interface ExaminationData {
  user: IUser
}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  @Input() user: IUser
  constructor(public dialog: MatDialog) { 
  }

  ngOnInit(): void {
  }

  startExamination(){
    this.dialog.open(ExaminationComponent,
      {
        data: {user: this.user, dialog: this.dialog}
      });
  }
}


