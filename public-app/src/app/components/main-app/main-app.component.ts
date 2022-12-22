import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-main-app',
  templateUrl: './main-app.component.html',
  styleUrls: ['./main-app.component.scss']
})
export class MainAppComponent implements OnInit {

  constructor() { }
  isLoggedin: boolean
  ngOnInit(): void {
  }

  login(event: any){
    this.isLoggedin = event
    console.log(this.isLoggedin)
  }

}
