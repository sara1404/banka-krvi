import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QrCodeScanComponent } from './qr-code-scan.component';
import { MaterialModule } from 'src/app/material/material.module';
import { NgxScannerQrcodeModule } from 'ngx-scanner-qrcode';

@NgModule({
  declarations: [QrCodeScanComponent],
  imports: [
    CommonModule,
    MaterialModule,
    NgxScannerQrcodeModule,
    
  ],
  exports: [QrCodeScanComponent]
})
export class QrCodeScanModule { }
