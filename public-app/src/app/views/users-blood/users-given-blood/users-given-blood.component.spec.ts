import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersGivenBloodComponent } from './users-given-blood.component';

describe('UsersGivenBloodComponent', () => {
  let component: UsersGivenBloodComponent;
  let fixture: ComponentFixture<UsersGivenBloodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersGivenBloodComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersGivenBloodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
