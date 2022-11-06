import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-info',
  templateUrl: './admin-info.component.html',
  styleUrls: ['./admin-info.component.scss']
})
export class AdminInfoComponent implements OnInit {

  constructor() { }

  public isDisabled: boolean = true;
  ngOnInit(): void {
  }
  editClick()
  {
    this.isDisabled = false;
  }
  okClick()
  {
    this.isDisabled = true;
  }

}
