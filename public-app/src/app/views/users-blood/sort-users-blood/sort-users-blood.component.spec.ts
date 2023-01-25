import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SortUsersBloodComponent } from './sort-users-blood.component';

describe('SortUsersBloodComponent', () => {
  let component: SortUsersBloodComponent;
  let fixture: ComponentFixture<SortUsersBloodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SortUsersBloodComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SortUsersBloodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
