import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterUserComponent } from './register-user/register-user.component';
import { MaterialModule } from 'src/app/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';



@NgModule({
  declarations: [
    RegisterUserComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [RegisterUserComponent]
})
export class RegisterUserModule { }
