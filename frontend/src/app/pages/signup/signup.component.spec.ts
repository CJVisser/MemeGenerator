import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SignupComponent } from './signup.component';
import { SignupService } from '../../../app/services/signup/signup.service';

describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;
  let mockSignupService;

  beforeEach(async () => {
    mockSignupService = jasmine.createSpyObj(['signup']);
    mockSignupService.signup.and.returnValue({subscribe: () => {}});

    await TestBed.configureTestingModule({
      declarations: [ SignupComponent ],
      imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      providers: [{provide: SignupService, useValue: mockSignupService}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not signup no username', () => {
    expect(mockSignupService.signup).not.toHaveBeenCalled();

    const testUser = {
      username: '',
      password: 'test',
      email: 'test'
    };

    component.signupForm.controls['username'].setValue(testUser.username);
    component.signupForm.controls['password'].setValue(testUser.password);
    component.signupForm.controls['email'].setValue(testUser.email);

    component.signup();

    expect(mockSignupService.signup).not.toHaveBeenCalled();
  })

  it('should not signup no password', () => {
    expect(mockSignupService.signup).not.toHaveBeenCalled();

    const testUser = {
      username: 'test',
      password: '',
      email: 'test'
    };

    component.signupForm.controls['username'].setValue(testUser.username);
    component.signupForm.controls['password'].setValue(testUser.password);
    component.signupForm.controls['email'].setValue(testUser.email);

    component.signup();

    expect(mockSignupService.signup).not.toHaveBeenCalled();
  })

  it('should not signup no email', () => {
    expect(mockSignupService.signup).not.toHaveBeenCalled();

    const testUser = {
      username: 'test',
      password: 'test',
      email: ''
    };

    component.signupForm.controls['username'].setValue(testUser.username);
    component.signupForm.controls['password'].setValue(testUser.password);
    component.signupForm.controls['email'].setValue(testUser.email);

    component.signup();

    expect(mockSignupService.signup).not.toHaveBeenCalled();
  })
  

  it('should signup', () => {
    expect(mockSignupService.signup).not.toHaveBeenCalled();

    const testUser = {
      username: 'test',
      password: 'test',
      email: 'test'
    };

    component.signupForm.controls['username'].setValue(testUser.username);
    component.signupForm.controls['password'].setValue(testUser.password);
    component.signupForm.controls['email'].setValue(testUser.email);

    component.signup();

    expect(mockSignupService.signup).toHaveBeenCalledTimes(1);
  })
});
