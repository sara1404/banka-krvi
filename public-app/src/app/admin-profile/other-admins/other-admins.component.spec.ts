import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtherAdminsComponent } from './other-admins.component';

describe('OtherAdminsComponent', () => {
  let component: OtherAdminsComponent;
  let fixture: ComponentFixture<OtherAdminsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OtherAdminsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OtherAdminsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
