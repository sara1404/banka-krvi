import { Component, OnInit, Inject, Input } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { IUser } from 'src/app/model/User';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  constructor(private authService: AuthService) { }

  @Input() isLoggedIn: boolean;
  user: IUser = {
    address: null,
    bloodBank: null,
    email: null,
    firstLogged: null,
    bloodType: null,
    gender: null,
    firstName: null,
    id: null,
    jmbg: null,
    jobTitle: null,
    lastName: null,
    password: null,
    phoneNumber: null,
    points: null,
    userType: null,
    workplaceName: null
  }
  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.authService.getCurrentUser().subscribe(data=>{this.user = data});
  }
}
