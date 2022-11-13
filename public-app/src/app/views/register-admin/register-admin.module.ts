import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RegisterAdminComponent } from './register-admin.component';


@NgModule({
  declarations: [RegisterAdminComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
  
  ],
  exports: [RegisterAdminComponent]
})
export class RegisterAdminModule { }
