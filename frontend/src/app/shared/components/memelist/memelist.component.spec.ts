import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemelistComponent } from './memelist.component';

describe('MemelistComponent', () => {
  let component: MemelistComponent;
  let fixture: ComponentFixture<MemelistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemelistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
