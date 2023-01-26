import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Route, RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminProfileModule } from './admin-profile/admin-profile.module';
import {MatFormFieldModule} from '@angular/material/form-field';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComponentsModule } from './components/components.module';
import { RegisterAdminComponent } from './views/register-admin/register-admin.component';
import { UsersModule } from './views/users/users.module';
import { MaterialModule } from './material/material.module';
import { BloodSuppliesComponent } from './admin-profile/blood-supplies/blood-supplies.component';
import { RegisterBloodbankComponent } from './views/register-bloodbank/register-bloodbank.component';
import { RegisterAdminModule } from './views/register-admin/register-admin.module';
import { RegisterBloodbankModule } from './views/register-bloodbank/register-bloodbank.module';
import { BloodBankDisplayModule } from './views/blood-banks-display/blood-bank-display.module';
import { UserProfileModule } from './views/user-profile/user-profile.module';
import {ToastrModule} from "ngx-toastr"
import { RegisterUserModule } from './views/register-user/register-user.module';
import { DonationSurveyModule } from './views/donation-survey/donation-survey.module';
import { CreateAppointmentModule } from './views/appointments/create-appointment.module';
import { UserLoginModule } from './views/user-login/user-login.module';
import { AdminCalendarModule } from './views/admin-calendar/admin-calendar.module';
import { JwtInterceptor } from './interceptor/jwt.interceptor';
import { CreateAppointmentUserModule } from './views/appointments/create-appointment-user/create-appointment-user.module';
import { PredefinedAppointmentsComponent } from './views/predefined-appointments/predefined-appointments.component';
import { PersonalAppointmentsComponent } from './views/personal-appointments/personal-appointments.component';
import { PredefinedAppointmentsModule } from './views/predefined-appointments/predefined-appointments.module';
import { QrCodeScanModule } from './views/qr/qr-code-scan/qr-code-scan.module';
import { AuthGuard } from './services/auth.guard';
import { AuthService } from './services/auth.service';
import { UsersBloodModule } from './views/users-blood/users-blood.module';
import { DeliveryMapModule } from './views/delivery-map/delivery-map.module';

@NgModule({
  declarations: [
    AppComponent,
    BloodSuppliesComponent,
    PredefinedAppointmentsComponent,
    PersonalAppointmentsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    BloodBankDisplayModule,
    BrowserAnimationsModule,
    AdminProfileModule,
    MatFormFieldModule,
    NgbModule,
    ComponentsModule,
    MaterialModule,
    UsersModule,
    UserProfileModule,
    RegisterAdminModule,
    RegisterBloodbankModule,
    RegisterUserModule,
    DonationSurveyModule,
    UserLoginModule,
    AdminCalendarModule,
    CreateAppointmentModule,
    CreateAppointmentUserModule,
    PredefinedAppointmentsModule,
    UsersBloodModule,
    ToastrModule.forRoot({
      positionClass: "toast-bottom-right"
    }),
    CreateAppointmentModule,
    QrCodeScanModule,
    DeliveryMapModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    AuthGuard,
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
