import { Component, OnInit, Inject } from '@angular/core';
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

  isLoggedIn: boolean;
  user: IUser;
  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    //this.authService.getLoggedUser().subscribe(data=>{this.user = data;});
    /*var interval = setInterval(() => {
      this.refresh(); // api call
    }, 200);*/
  }
  /*refresh(){
    this.ngOnInit();
  }*/

}
