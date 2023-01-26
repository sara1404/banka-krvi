import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeliveryMapComponent } from './delivery-map.component';
import { MaterialModule } from 'src/app/material/material.module';



@NgModule({
  declarations: [
    DeliveryMapComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [DeliveryMapComponent]
})
export class DeliveryMapModule { }
