import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MememakerpageComponent } from './mememakerpage.component';

describe('MememakerpageComponent', () => {
  let component: MememakerpageComponent;
  let fixture: ComponentFixture<MememakerpageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MememakerpageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MememakerpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
