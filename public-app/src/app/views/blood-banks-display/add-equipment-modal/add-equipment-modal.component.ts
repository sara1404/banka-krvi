import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IBloodBank } from 'src/app/model/BloodBankk';
import { ClickedAppointmentComponent } from '../../admin-calendar/clicked-appointment/clicked-appointment.component';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { EquipmentService } from 'src/app/services/equipment.service';

@Component({
  selector: 'app-add-equipment-modal',
  templateUrl: './add-equipment-modal.component.html',
  styleUrls: ['./add-equipment-modal.component.scss']
})
export class AddEquipmentModalComponent implements OnInit {

  bloodbank: IBloodBank

  equipmentForm = new FormGroup({
    equipmentType : new FormControl('', [Validators.required]),
    quantity: new FormControl(0, [Validators.required]),
  })


  constructor(public dialogRef: MatDialogRef<ClickedAppointmentComponent>, @Inject(MAT_DIALOG_DATA) private _data: any, private equipmentService: EquipmentService) { }

  ngOnInit(): void {
    this.bloodbank = this._data.bloodbank;
  }

  addNewEquipment(){
    this.equipmentService.addNewEquipment({bloodBank: this.bloodbank, equipmentType: this.equipmentForm.value.equipmentType, quantity: this.equipmentForm.value.quantity})
      .subscribe()
  }

}
