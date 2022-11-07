import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-other-admins',
  templateUrl: './other-admins.component.html',
  styleUrls: ['./other-admins.component.scss']
})
export class OtherAdminsComponent implements OnInit {

  constructor() { }

  displayedColumns: string[] = ["name", "surname", "email"]
  ngOnInit(): void {
  }

}
