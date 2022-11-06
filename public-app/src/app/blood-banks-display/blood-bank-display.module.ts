import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';

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
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule,
    MatSelectModule
  ], 
  exports: [DisplayBloodBanksComponent],
})
export class BloodBankDisplayModule { }
