import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { BaseConfig, NgxScannerQrcodeService, ScannerQRCodeResult } from 'ngx-scanner-qrcode';
import QrcodeDecoder from 'qrcode-decoder';
import { tap } from 'rxjs';
import { IAppointment } from 'src/app/model/Appointment';
import { IUser } from 'src/app/model/User';
import { AppointmentService } from 'src/app/services/appointment.service';
import { UserService } from 'src/app/services/user.service';
import { AppointmentInfoComponent } from './appointment-info/appointment-info.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit, AfterViewInit {

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
  appointment: IAppointment
  users: IUser[] = []
  userCount: number
  currentInput:any;
  @ViewChild(MatPaginator)
  paginator: MatPaginator
  public qrCodeResult: ScannerQRCodeResult[] = [];

  constructor(private userService: UserService, private qrcode: NgxScannerQrcodeService, private appointmentService: AppointmentService) { }
  ngAfterViewInit(): void {
    this.paginator.page.pipe(tap(() => {
      this.userService.getUsers(this.paginator?.pageIndex ?? 0).subscribe(data =>{
        this.users = data
      })
    })).subscribe()
  }

  ngOnInit(): void {
    this.userService.getUsersCount().subscribe(data => this.userCount = data)
    this.userService.getUsers(this.paginator?.pageIndex ?? 0).subscribe(data =>{
      this.users = data
    })
  }

  search(e: any){
    e.preventDefault()
    this.userService.search(e.target.value).subscribe(data => {
      this.users = data
    })
  }

  uploadQrCode(e: any){
    console.log(this.currentInput)
    const target = e.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
        console.log(target.files[0].name);
    }
  }

  public onSelects(files: any) {
    this.qrcode.loadFiles(files, Object.assign({isDraw: true, isBeep: true}, this.config)).subscribe((res: ScannerQRCodeResult[]) => {
      this.qrCodeResult = res.filter(f => f.urlQR);
      this.appointmentService.getById(this.qrCodeResult[0].data).subscribe(data => {
        this.appointment = data
        console.log(this.appointment)
      })
      console.log(this.qrCodeResult[0].data)
    });
  }

}
