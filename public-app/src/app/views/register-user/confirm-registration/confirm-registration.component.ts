import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-confirm-registration',
  templateUrl: './confirm-registration.component.html',
  styleUrls: ['./confirm-registration.component.scss']
})
export class ConfirmRegistrationComponent implements OnInit {
  message: string ='Please wait while we process your request!';
  constructor(private userService: UserService, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(s => {
      console.log(s["email"])
      this.userService.confirmUserRegistration(s['email']).subscribe(
        (res) => {
          this.message = "Succesfully activated user account!";
        }, 
        (err) => {
          this.message = 'Something went wrong!';
        }
      )
    });
  }

}
