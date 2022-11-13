import { Component, OnInit } from '@angular/core';
import { IUser } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  constructor(private userService: UserService) { }

  public isDisabled : boolean = true;
  public user : IUser
  ngOnInit(): void {
    //this.userService.getRegisteredUserProfile().subscribe((data) => (this.user = data));
    this.user = this.userService.getRegisteredUserProfile();
  }

  okClick(firstName: string, lastName: string, jbmgString: string, bloodType: string, email: string){
    var jmbg = parseInt(jbmgString);
    this.userService.updateUserProfile({id: this.user.id, firstName: firstName, lastName: lastName, jmbg: jmbg, email: email, bloodType: bloodType, bloodBank: this.user.bloodBank}).subscribe((data) => this.user = data);
    this.isDisabled = true;
  }

  editClick(){
    this.isDisabled = false;
  }

}
