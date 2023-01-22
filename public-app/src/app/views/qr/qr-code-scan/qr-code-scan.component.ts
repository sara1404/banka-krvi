import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { BaseConfig, NgxScannerQrcodeService, ScannerQRCodeResult } from 'ngx-scanner-qrcode';
import { IAppointment } from 'src/app/model/Appointment';
import { IUserSurvey } from 'src/app/model/UserSurvey';
import { AppointmentService } from 'src/app/services/appointment.service';
import { UserService } from 'src/app/services/user.service';
import { ClickedAppointmentComponent } from '../../admin-calendar/clicked-appointment/clicked-appointment.component';

@Component({
  selector: 'app-qr-code-scan',
  templateUrl: './qr-code-scan.component.html',
  styleUrls: ['./qr-code-scan.component.scss']
})
export class QrCodeScanComponent implements OnInit {

  public config: BaseConfig = {
    // isAuto: false,
    isDraw: false,
    // isBeep: true,
    // isAlwaysEmit: true,
    text: { 
      font: '25px serif', // Hiden { font: '0px', bottom: 40 },
      fillStyle: 'red' 
    },
    frame: {
      strokeStyle: 'red'
    },
    medias: {
      audio: false,
      video: {
        width: { ideal: 1280 },
        height: { ideal: 720 },
        facingMode: 'user', // Front and back camera { facingMode: front ? "user" : "environment" }
      }
    } // https://developer.mozilla.org/en-US/docs/Web/API/MediaDevices/getUserMedia
  };

  appointment: IAppointment;
  parsed: string[] = []

  public qrCodeResult: ScannerQRCodeResult[] = [];

  constructor(private qrcode: NgxScannerQrcodeService, private appointmentService: AppointmentService, private userService: UserService,  public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  public onSelects(files: any) {
    this.qrcode.loadFiles(files, Object.assign({isDraw: true, isBeep: true}, this.config)).subscribe((res: ScannerQRCodeResult[]) => {
      this.qrCodeResult = res.filter(f => f.urlQR);
      this.parsed = this.qrCodeResult[0].data.split(',')

      this.appointmentService.getById(this.parsed[0].split(':')[1].trim()).subscribe(data => {
        this.appointment = data
        this.click()
      })
      
    });
  }


  click(){
    this.dialog.open(ClickedAppointmentComponent,
      {
        data: {appointment: this.appointment, user:this.appointment.user}
      });
  }
}
