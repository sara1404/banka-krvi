import { Component, OnInit } from '@angular/core';
import { IAdministrator } from '../model/Administrator';
import { IUser } from '../model/User';
import { AdminInfoService } from '../service/admin-info.service';

@Component({
  selector: 'app-other-admins',
  templateUrl: './other-admins.component.html',
  styleUrls: ['./other-admins.component.scss']
})
export class OtherAdminsComponent implements OnInit {

  constructor(private adminInfoService: AdminInfoService) { }

  displayedColumns: string[] = ["name", "surname", "email"]
  public otherAdmins: IAdministrator[];
  ngOnInit(): void {
    this.adminInfoService.getOtherAdministrators().subscribe(data=>{this.otherAdmins = data;});
  }

}
