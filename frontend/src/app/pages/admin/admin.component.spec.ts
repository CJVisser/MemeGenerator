import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AdminComponent } from './admin.component';
import { RouterTestingModule } from '@angular/router/testing';
import { LoginService } from '../../../app/services/login/loginService';
import { AdminService } from '../../../app/services/admin/adminService';

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;
  let mockLoginService;
  let mockAdminService;

  beforeEach(async () => {
    mockLoginService = jasmine.createSpyObj(['getCurrentUser']);
    mockLoginService.getCurrentUser.and.returnValue({roles: 'test'});

    mockAdminService = jasmine.createSpyObj(['getUsers', 'banUser', 'cancelMeme']);
    mockAdminService.getUsers.and.returnValue({subscribe: () => {}});
    mockAdminService.banUser.and.returnValue({subscribe: () => {}});
    mockAdminService.cancelMeme.and.returnValue({subscribe: () => {}});
    await TestBed.configureTestingModule({
      declarations: [ AdminComponent ],
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [{provide: LoginService, useValue: mockLoginService}, {provide: AdminService, useValue: mockAdminService}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should call ban user', () => {
    expect(mockAdminService.banUser).not.toHaveBeenCalled();

    component.banUser(1);

    expect(mockAdminService.banUser).toHaveBeenCalledTimes(1);
  })

  it('Should call cancel meme', () => {
    expect(mockAdminService.cancelMeme).not.toHaveBeenCalled();

    component.cancelMeme(1);

    expect(mockAdminService.cancelMeme).toHaveBeenCalledTimes(1);
  })
});
