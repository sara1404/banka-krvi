import { Component, OnInit } from '@angular/core';
import { IBloodBank } from 'src/app/model/BloodBankk';
import { BloodBankService } from 'src/app/services/blood-bank.service';

@Component({
  selector: 'app-register-admin',
  templateUrl: './register-admin.component.html',
  styleUrls: ['./register-admin.component.scss']
})
export class RegisterAdminComponent implements OnInit {

  bloodbanks: IBloodBank[] = []
  constructor(private bloodbankService: BloodBankService) { }

  ngOnInit(): void {
    this.bloodbankService.getBloodBanks().subscribe((data) => this.bloodbanks = data);
  }

  registerAdmin(){
    
  }

}
