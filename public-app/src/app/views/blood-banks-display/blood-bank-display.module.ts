import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';

import { DisplayBloodBanksComponent } from './blood-banks-display.component';
import { SearchBloodBanksComponent } from './search-blood-banks/search-blood-banks.component';
import { FilterBloodBanksComponent } from './filter-blood-banks/filter-blood-banks.component';

@NgModule({
  declarations: [
    DisplayBloodBanksComponent,
    SearchBloodBanksComponent,
    FilterBloodBanksComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ], 
  exports: [DisplayBloodBanksComponent],
})
export class BloodBankDisplayModule { }
