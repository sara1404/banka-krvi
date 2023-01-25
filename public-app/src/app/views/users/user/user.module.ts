import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';
import { ExaminationComponent } from '../examination/examination.component';
import { MatDialog } from '@angular/material/dialog';



@NgModule({
  declarations: [],
  entryComponents: [ExaminationComponent],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class UserModule { }
