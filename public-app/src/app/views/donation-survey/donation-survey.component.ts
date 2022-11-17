import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/services/toast.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-donation-survey',
  templateUrl: './donation-survey.component.html',
  styleUrls: ['./donation-survey.component.scss'],
})
export class DonationSurveyComponent implements OnInit {
  public surveyForm: FormGroup;

  constructor(fb: FormBuilder,private userService: UserService, private toastService: ToastService, private router: Router) {
    this.surveyForm = fb.group({
      weight: [0, [Validators.required, Validators.min(0), Validators.max(250)]],
      fluSymptoms: [true, [Validators.required]],
      skinIrritations: [true, [Validators.required]],
      abnormalBloodPressure: [true, [Validators.required]],
      tookAntibiotics: [true, [Validators.required]],
      onPeriod: [true, [Validators.required]],
      dentistIntervention: [true, [Validators.required]],
      piercingOrTattoo: [true, [Validators.required]],
    });
  }

  ngOnInit(): void {}

  sendSurvey() {
    if(this.surveyForm.valid) {
      this.userService.sendSurvey(this.surveyForm.value).subscribe(
        (res) => {
          this.toastService.showSuccess("Successfully sent survey!");
          this.router.navigate(['/']);
        },
        (err) => {
          this.toastService.showError("Error occured while sending survey, status code: " + err.status);
        }
      );
    }
  }
}
