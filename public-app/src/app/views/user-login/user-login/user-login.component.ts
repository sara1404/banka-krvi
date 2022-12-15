import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ToastService } from 'src/app/services/toast.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss'],
})
export class UserLoginComponent implements OnInit {
  public form: FormGroup;

  constructor(
    fb: FormBuilder,
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {
    this.form = fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  get email() {
    return this.form.get('email');
  }
  get password() {
    return this.form.get('password');
  }

  ngOnInit(): void {}
  login() {
    if (this.form.valid) {
      this.authService.loginUser(this.form.value).subscribe(
        (res) => {
          this.authService.setSession(res.token);
          this.authService.getCurrentUser().subscribe(data => {
            console.log(data)
            if(data.firstLogged === false)
              this.router.navigate(['/app/changePassword']).then(() => {
                window.location.reload();
              })
              return
          })
          this.router.navigate(['/app']).then(() => {
            window.location.reload();
          });

        },
        (err) => {
          this.toastService.showError(
            'Invalid credentials! Error: ' + err.status
          );
        }
      );
    }
  }
}
