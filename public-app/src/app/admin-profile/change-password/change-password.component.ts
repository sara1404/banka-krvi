import { Component, OnInit } from '@angular/core';
import { AdminInfoService } from '../service/admin-info.service';
import { IPasswordChange } from '../model/PasswordChange';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  constructor(private adminInfoService: AdminInfoService) { }

  public empty: boolean = false;
  public okPassword: boolean = true;
  public updatedPassword: boolean = false;
  public okPasswordText: string = "";
  result: Boolean = false;

  ngOnInit(): void {
  }

  validate(oldPassword: string, newPassword: string):boolean{
    this.updatedPassword = false;
    if(oldPassword == "" || newPassword == ""){
      return false;
    }
    if(newPassword.length < 6 ){
      this.okPassword = false;
      this.okPasswordText = "Password must be 6 characters long";
      return false;
    }else{
      this.okPassword = true;
    }

    return true;
  }
  change(oldPassword: string, newPassword: string){
    if(!this.validate(oldPassword, newPassword)){
      this.empty = true;
      return;
    }else{
      this.empty = false;
    }
    const updatedPassword: IPasswordChange = {
      oldPassword: oldPassword,
      newPassword: newPassword
    };

    this.adminInfoService.changePassword(updatedPassword).subscribe(data=>{this.result = data;
      if(data==true){
        this.updatedPassword = true;
        console.log('promenjeno')
      }else{
        this.okPassword = false;
      this.okPasswordText = "Invalid old password";
      this.updatedPassword = false;
      }
    });
  }
}
