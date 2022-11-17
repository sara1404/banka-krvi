import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { BloodBankService } from 'src/app/services/blood-bank.service';
import { ToastService } from 'src/app/services/toast.service';
import { Router } from '@angular/router';

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
    name : new FormControl(null),
    description: new FormControl(null),
    street: new FormControl(null),
    number: new FormControl(null),
    city: new FormControl(null),
    zipcode: new FormControl(null),
    country: new FormControl(null),
    address: new FormControl(null)
  })

  matcher = new MyErrorStateMatcher();

  constructor(private bloodBankService: BloodBankService, private toastService: ToastService, private router:Router) { }

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
    this.bloodBankService.registerBloodbank(this.registerForm.value).subscribe({
      next: (res) => {
        this.showSuccess()
        this.router.navigate(['/', 'bloodBanks'])
      },
      error: (e) => {
        this.showError(e)
      },
    })

  }

  showError(e) {
    this.toastService.showError("Error!");
  }

  showSuccess() {
    this.toastService.showSuccess('Successfully registered bloodbank.');

  }

}
