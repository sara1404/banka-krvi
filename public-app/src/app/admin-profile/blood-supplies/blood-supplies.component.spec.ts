import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodSuppliesComponent } from './blood-supplies.component';

describe('BloodSuppliesComponent', () => {
  let component: BloodSuppliesComponent;
  let fixture: ComponentFixture<BloodSuppliesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodSuppliesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloodSuppliesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
