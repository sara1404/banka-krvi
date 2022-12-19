import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClickedAppointmentComponent } from './clicked-appointment.component';

describe('ClickedAppointmentComponent', () => {
  let component: ClickedAppointmentComponent;
  let fixture: ComponentFixture<ClickedAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClickedAppointmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClickedAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
