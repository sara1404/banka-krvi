import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { tap } from 'rxjs';
import { IUser } from 'src/app/model/User';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit, AfterViewInit {

  users: IUser[] = []
  userCount: number

  @ViewChild(MatPaginator)
  paginator: MatPaginator

  constructor(private userService: UserService) { }
  ngAfterViewInit(): void {
    this.paginator.page.pipe(tap(() => {
      this.userService.getUsers(this.paginator?.pageIndex ?? 0).subscribe(data =>{
        this.users = data
      })
    })).subscribe()
  }

  ngOnInit(): void {
    this.userService.getUsersCount().subscribe(data => this.userCount = data)
    this.userService.getUsers(this.paginator?.pageIndex ?? 0).subscribe(data =>{
      this.users = data
    })
  }

  search(e: any){
    e.preventDefault()
    this.userService.search(e.target.value).subscribe(data => {
      this.users = data
    })
  }

}
