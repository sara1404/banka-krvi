import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../material/material.module';
import { CreateAppointmentUserComponent } from './create-appointment-user.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CreateAppointmentDialogComponent } from './create-appointment-dialog/create-app-dialog.component';



@NgModule({
  declarations: [
    CreateAppointmentUserComponent,
    CreateAppointmentDialogComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class CreateAppointmentUserModule { }
