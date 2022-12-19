import { Component, Inject, Injector, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { toJSDate } from '@ng-bootstrap/ng-bootstrap/datepicker/ngb-calendar';
import { IBloodBank } from 'src/app/model/BloodBankk';
import { IUser } from 'src/app/model/User';
import { BloodBankService } from 'src/app/services/blood-bank.service';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';



export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-register-admin',
  templateUrl: './register-admin.component.html',
  styleUrls: ['./register-admin.component.scss']
})
export class RegisterAdminComponent implements OnInit {

  bloodbanks: IBloodBank[] = []
  registerForm = new FormGroup({
    firstName : new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    jmbg: new FormControl('', [Validators.required, Validators.minLength(13), Validators.maxLength(13)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    bloodType: new FormControl(null, [Validators.required]),
    adminType: new FormControl('', [Validators.required]),
    bloodBankName: new FormControl('', [Validators.required]),
    bloodBank: new FormControl(null),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    gender: new FormControl('', [Validators.required]),
    phoneNumber: new FormControl('', [Validators.required])
  })

  matcher = new MyErrorStateMatcher();

  constructor(private bloodbankService: BloodBankService, private userService: UserService, private toastService: ToastService, private router: Router) { }

  ngOnInit(): void {
    this.bloodbankService.getBloodBanks().subscribe((data) => this.bloodbanks = data);
  }

  registerAdmin(){
    this.registerForm.value.bloodBank = this.findBloodBankByName()
    this.userService.registerAdmin(this.registerForm.value).subscribe({
      next: (res) => {
        this.showSuccess()
        this.router.navigate(['/', 'users'])
      },
      error: (e) => {
        this.showError(e)
      },
    })
  }

  showError(e) {
    this.toastService.showError("Error");
  }

  showSuccess() {
    this.toastService.showSuccess('Successfully registered center admin.');

  }


  findBloodBankByName(){
    for(let bloodbank of this.bloodbanks){
      if(bloodbank.name === this.registerForm.value.bloodBankName)
        return bloodbank;
    }
    return null;
  }

}
