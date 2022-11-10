import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { IBloodBank } from 'src/app/model/BloodBankk';
import { IUser } from 'src/app/model/User';
import { BloodBankService } from 'src/app/services/blood-bank.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register-admin',
  templateUrl: './register-admin.component.html',
  styleUrls: ['./register-admin.component.scss']
})
export class RegisterAdminComponent implements OnInit {

  bloodbanks: IBloodBank[] = []
  registerForm = new FormGroup({
    firstName : new FormControl(''),
    lastName: new FormControl(''),
    jmbg: new FormControl(''),
    email: new FormControl(''),
    bloodType: new FormControl(''),
    bloodBank: new FormControl('')
  })

  constructor(private bloodbankService: BloodBankService, private userService: UserService) { }

  ngOnInit(): void {
    this.bloodbankService.getBloodBanks().subscribe((data) => this.bloodbanks = data);
  }

  registerAdmin(){
    this.userService.registerAdmin(this.registerForm)
  }

}
