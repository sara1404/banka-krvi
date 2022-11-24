import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material.module';

import { DisplayBloodBanksComponent } from './blood-banks-display.component';
import { SearchBloodBanksComponent } from './search-blood-banks/search-blood-banks.component';
import { FilterBloodBanksComponent } from './filter-blood-banks/filter-blood-banks.component';
import { SortBloodBanksComponent } from './sort-blood-banks/sort-blood-banks.component';

@NgModule({
  declarations: [
    DisplayBloodBanksComponent,
    SearchBloodBanksComponent,
    FilterBloodBanksComponent,
    SortBloodBanksComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ], 
  exports: [DisplayBloodBanksComponent],
})
export class BloodBankDisplayModule { }
