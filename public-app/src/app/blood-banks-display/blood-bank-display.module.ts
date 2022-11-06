import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import {
  MatFormField,
  MatFormFieldModule,
  MatLabel,
} from '@angular/material/form-field';
import { MatOption, MatOptionModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';

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
    MatSelectModule,
    MatFormFieldModule,
    MatOptionModule,
    MatInputModule,
  ], 
  exports: [DisplayBloodBanksComponent],
})
export class BloodBankDisplayModule { }
