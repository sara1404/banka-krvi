import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodbankInfoComponent } from './bloodbank-info.component';

describe('BloodbankInfoComponent', () => {
  let component: BloodbankInfoComponent;
  let fixture: ComponentFixture<BloodbankInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodbankInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloodbankInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
