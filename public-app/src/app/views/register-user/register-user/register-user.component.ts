import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  Form,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { isNumber } from '@ng-bootstrap/ng-bootstrap/util/util';
import { IUser } from 'src/app/model/User';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.scss'],
})
export class RegisterUserComponent implements OnInit {
  signUpForm: FormGroup;
  user: IUser;

  constructor(fb: FormBuilder, private userService: UserService, private toastService: ToastService) {
    this.signUpForm = fb.group({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      jmbg: new FormControl('', [
        Validators.required,
        Validators.minLength(13),
        Validators.maxLength(13),
      ]),
      email: new FormControl('', [Validators.required, Validators.email]),
      address: fb.group({
        street: new FormControl(),
        number: new FormControl(),
        city: new FormControl(),
        zipcode: new FormControl(),
        country: new FormControl(),
      }),
      phoneNumber: new FormControl(),
      gender: new FormControl(),
      bloodType: new FormControl('', [Validators.required]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
      ]),
      confirmPassword: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
      ]),
    });
  }

  ngOnInit(): void {}

  get firstName() {
    return this.signUpForm.get('firstName');
  }
  get lastName() {
    return this.signUpForm.get('lastName');
  }
  get jmbg() {
    return this.signUpForm.get('jmbg');
  }
  get email() {
    return this.signUpForm.get('email');
  }
  get bloodType() {
    return this.signUpForm.get('bloodType');
  }
  get password() {
    return this.signUpForm.get('password');
  }
  get confirmPassword() {
    return this.signUpForm.get('confirmPassword');
  }

  registerUser() {
    if(!this.signUpForm.valid) {
      return;
    }
    this.userService.registerUser(this.signUpForm.value).subscribe(
      (res) => {
        this.toastService.showSuccess('Successful user registration!');
      },
      (err) => {
        this.toastService.showError('Could not register user, status code: ' + err.status);
      }
    )
  }

  createCompareValidator(
    controlOne: AbstractControl,
    controlTwo: AbstractControl
  ) {
    return () => {
      return null;
      if (controlOne.value !== controlTwo.value)
        return { match_error: 'Value does not match' };
      return null;
    };
  }
}
