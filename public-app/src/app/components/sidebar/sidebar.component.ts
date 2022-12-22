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
  user: IUser;
  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
  }
  refresh(){
    this.ngOnInit();
  }

}
