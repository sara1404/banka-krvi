import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { BloodBankService } from 'src/app/services/blood-bank.service';

@Component({
  selector: 'app-register-bloodbank',
  templateUrl: './register-bloodbank.component.html',
  styleUrls: ['./register-bloodbank.component.scss']
})
export class RegisterBloodbankComponent implements OnInit {

  registerForm = new FormGroup({
    name : new FormControl(''),
    description: new FormControl(''),
    street: new FormControl(''),
    number: new FormControl(''),
    city: new FormControl(''),
    zipcode: new FormControl(''),
    country: new FormControl(''),
  })
  constructor(private bloodBankService: BloodBankService) { }

  ngOnInit(): void {
  }

  registerBloodbank(){
    this.bloodBankService.registerBloodbank(this.registerForm.value).subscribe()
  }

}
