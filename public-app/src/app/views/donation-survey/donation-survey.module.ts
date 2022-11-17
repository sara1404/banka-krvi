import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DonationSurveyComponent } from './donation-survey.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    DonationSurveyComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [DonationSurveyComponent]
})
export class DonationSurveyModule { }
