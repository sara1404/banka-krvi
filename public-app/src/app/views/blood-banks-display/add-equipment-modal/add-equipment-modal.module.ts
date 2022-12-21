import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddEquipmentModalComponent } from './add-equipment-modal.component';
import { MaterialModule } from 'src/app/material/material.module';


@NgModule({
  declarations: [AddEquipmentModalComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  exports: [AddEquipmentModalComponent]
})
export class AddEquipmentModalModule { }
