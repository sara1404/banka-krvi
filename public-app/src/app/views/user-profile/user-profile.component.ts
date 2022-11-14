import { Component, OnInit } from '@angular/core';
import { IUser } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';
import { FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ToastService } from 'src/app/services/toast.service';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}


@Component({
  selector: 'app-users',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  constructor(private userService: UserService, private toastService: ToastService) { }

  userProfileForm = new FormGroup({
    id : new FormControl({}),
    firstName : new FormControl({value : null, disabled: true}, [Validators.required]),
    lastName: new FormControl({value : null, disabled: true}, [Validators.required]),
    jmbg: new FormControl({value : null, disabled: true}, [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(13), Validators.maxLength(13)]),
    bloodType: new FormControl({value : null, disabled: true}, [Validators.required]),
    email: new FormControl({value: null, disabled: true}, [Validators.required, Validators.email]),
    bloodBank : new FormControl({}),
  })

  matcher = new MyErrorStateMatcher();

  ngOnInit(): void {
    this.userService.getLoggedInUserProfile().subscribe((data) => {
      this.userProfileForm.patchValue({
          id : data.id,
          firstName : data.firstName,
          lastName : data.lastName,
          jmbg : data.jmbg,
          bloodType : data.bloodType,
          email : data.email,
          bloodBank : data.bloodBank 
      })
    });
  }

  saveClick(e : Event){
    e.preventDefault();
    this.userService.updateUserProfile(this.userProfileForm.getRawValue()).subscribe({
      next: (res) => {
        this.toastService.showSuccess("Successfuly updated")
      },
      error: (e) => {
        console.log("error")
        this.toastService.showError("Error")
      },
    })
    this.userProfileForm.controls['firstName'].disable();
    this.userProfileForm.controls['lastName'].disable();
    this.userProfileForm.controls['jmbg'].disable();
    this.userProfileForm.controls['bloodType'].disable();
  }

  editClick(){
    this.userProfileForm.controls['firstName'].enable();
    this.userProfileForm.controls['lastName'].enable();
    this.userProfileForm.controls['jmbg'].enable();
    this.userProfileForm.controls['bloodType'].enable();
  }

}
