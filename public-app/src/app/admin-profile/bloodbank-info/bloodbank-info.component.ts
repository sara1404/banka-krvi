import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bloodbank-info',
  templateUrl: './bloodbank-info.component.html',
  styleUrls: ['./bloodbank-info.component.scss']
})
export class BloodbankInfoComponent implements OnInit {

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
