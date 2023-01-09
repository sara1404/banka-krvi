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
    phoneNumber: new FormControl({value: null, disabled: true}, [Validators.required, Validators.pattern("^[0-9]{10}$")]),
    gender: new FormControl({value: null, disabled: true}, [Validators.required]),
    jobTitle: new FormControl({value: null, disabled: true}, [Validators.required]),
    workplaceName: new FormControl({value: null, disabled: true}),
    address: new FormGroup({
      id: new FormControl(),
      street : new FormControl({value : null, disabled: true}, [Validators.required]),
      number : new FormControl({value : null, disabled : true}, [Validators.required]),
      city : new FormControl({value : null, disabled : true}, [Validators.required]),
      zipcode : new FormControl({value : null, disabled : true}, [Validators.required]),
      country : new FormControl({value : null, disabled : true}, [Validators.required]),
      longitude: new FormControl(),
      latitude: new FormControl()
    }),
    bloodType: new FormControl({value : null, disabled: true}, [Validators.required]),
    email: new FormControl({value: null, disabled: true}, [Validators.required, Validators.email]),
    bloodBank : new FormControl({}),
  })

  matcher = new MyErrorStateMatcher();

  ngOnInit(): void {
    this.userService.getLoggedInUserProfile().subscribe((data) => 
      this.userProfileForm.patchValue(data)
    );
  }

  saveClick(e : Event){
    e.preventDefault();
    this.userService.updateUserProfile(this.userProfileForm.getRawValue()).subscribe({
      next: (res) => {
        this.toastService.showSuccess("Successfuly updated")
      },
      error: (err) => {
        console.log("error")
        this.toastService.showError("Error occured while updating profile, status code: " + err.status)
      },
    })
    this.userProfileForm.controls['firstName'].disable();
    this.userProfileForm.controls['lastName'].disable();
    this.userProfileForm.controls['jmbg'].disable();
    this.userProfileForm.controls['bloodType'].disable();
    this.userProfileForm.controls['phoneNumber'].disable();
    this.userProfileForm.controls['jobTitle'].disable();
    this.userProfileForm.controls['workplaceName'].disable();
    this.userProfileForm.controls['gender'].disable();
    this.userProfileForm.controls['address'].disable();
  }

  editClick(){
    this.userProfileForm.controls['firstName'].enable();
    this.userProfileForm.controls['lastName'].enable();
    this.userProfileForm.controls['jmbg'].enable();
    this.userProfileForm.controls['bloodType'].enable();
    this.userProfileForm.controls['phoneNumber'].enable();
    this.userProfileForm.controls['jobTitle'].enable();
    this.userProfileForm.controls['workplaceName'].enable();
    this.userProfileForm.controls['gender'].enable();
    this.userProfileForm.controls['address'].enable();
  }

}
