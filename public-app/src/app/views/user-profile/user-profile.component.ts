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

  public isDisabled : boolean = true;
  public user : IUser = {firstName:'', lastName: '', jmbg : 0, email: '', bloodType: '', bloodBank: null, id: 0};
  userProfileForm = new FormGroup({
    firstName : new FormControl({value : this.user.firstName, disabled: true}, [Validators.required]),
    lastName: new FormControl({value : this.user.lastName, disabled: true}, [Validators.required]),
    jmbg: new FormControl({value : this.user.jmbg, disabled: true}, [Validators.required, Validators.minLength(13), Validators.maxLength(13)]),
    bloodType: new FormControl({value : this.user.bloodType, disabled: true}, [Validators.required]),
    email: new FormControl({value: this.user.email, disabled: true}, [Validators.required, Validators.email]),
  })

  matcher = new MyErrorStateMatcher();

  ngOnInit(): void {
    this.userService.getLoggedInUserProfile().subscribe((data) => (this.user = data));
  }

  saveClick(e : Event, firstName: string, lastName: string, jbmgString: string, bloodType: string, email: string){
    e.preventDefault();
    var jmbg = parseInt(jbmgString);
    this.user = {id: this.user.id, firstName: firstName, lastName: lastName, jmbg: jmbg, email: email, bloodType: bloodType, bloodBank: this.user.bloodBank};
    this.userService.updateUserProfile(this.user).subscribe({
      next: (res) => {
        this.user = res;
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
