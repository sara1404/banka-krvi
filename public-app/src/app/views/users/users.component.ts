import { Component, OnInit } from '@angular/core';
import { Event } from '@angular/router';
import { IUser } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  users: IUser[] = []
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data =>{
      this.users = data
    })
  }

  search(e: any){
    e.preventDefault()
    this.userService.search(e.target.value).subscribe(data => {
      this.users = data
      console.log('search ', this.users)
    })
  }

}
