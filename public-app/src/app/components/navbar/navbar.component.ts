import { Component, OnInit } from '@angular/core';
import { IUser } from 'src/app/model/User';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthService) { }

  isLoggedIn:boolean;
  user: IUser;

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.authService.getCurrentUser().subscribe(data=>{this.user = data; console.log(this.user)});
    //console.log(this.isLoggedIn);

  }

  logout() {
    this.authService.logout();
    this.isLoggedIn = false;
  }
}
