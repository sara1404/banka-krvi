import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterBloodbankComponent } from './register-bloodbank.component';
import { MaterialModule } from 'src/app/material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
  declarations: [RegisterBloodbankComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [RegisterBloodbankComponent]
})
export class RegisterBloodbankModule { }
