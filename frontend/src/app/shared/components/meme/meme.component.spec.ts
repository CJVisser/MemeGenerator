import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { MemeComponent } from './meme.component';
import { LoginService } from '../../../../app/services/login/loginService';

describe('MemeComponent', () => {
  let component: MemeComponent;
  let fixture: ComponentFixture<MemeComponent>;
  let mockLoginService;

  const memeUser = {
    username: 'test'
  }

  beforeEach(async () => {
    mockLoginService = jasmine.createSpyObj(['getCurrentUser']);
    mockLoginService.getCurrentUser.and.returnValue({username: 'test'});

    await TestBed.configureTestingModule({
      declarations: [ MemeComponent ],
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [{provide: LoginService, useValue: mockLoginService}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemeComponent);
    component = fixture.componentInstance;
    component.memeUser = memeUser;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
