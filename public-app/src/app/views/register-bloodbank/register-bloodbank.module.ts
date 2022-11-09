import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterBloodbankComponent } from './register-bloodbank.component';
import { MaterialModule } from 'src/app/material/material.module';


@NgModule({
  declarations: [RegisterBloodbankComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [RegisterBloodbankComponent]
})
export class RegisterBloodbankModule { }
