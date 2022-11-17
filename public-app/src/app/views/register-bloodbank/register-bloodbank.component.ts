import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { BloodBankService } from 'src/app/services/blood-bank.service';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

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
    address: new FormControl(null)
  })

  matcher = new MyErrorStateMatcher();

  constructor(private bloodBankService: BloodBankService) { }

  ngOnInit(): void {
  }

  registerBloodbank(){
    this.registerForm.value.address = {
      street: this.registerForm.value.street,
      number: this.registerForm.value.number,
      city: this.registerForm.value.city,
      zipcode: this.registerForm.value.zipcode,
      country: this.registerForm.value.country
    }
    this.bloodBankService.registerBloodbank(this.registerForm.value).subscribe()
    this.registerForm.reset(this.registerForm.value)
  }

}
