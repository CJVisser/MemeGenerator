import { TestBed } from '@angular/core/testing';
import { AdminService } from './adminService';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { User } from '../../../app/models/User';
import { environment } from '../../../environments/environment';

describe('AdminService', () => {
  let service: AdminService;

  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(AdminService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  // Test fetching all
  it('should get all users', () => {

    const fakeUsers : User[] = []

    for(let i = 0; i < 5; i ++){
      const users: User = {
        username: `username ${i}`,
        email: `email ${i}`,
        password: `password ${i}`
      };

      fakeUsers.push(users)
    }

    service.getUsers().subscribe((c)=>{
      expect(c).toEqual(fakeUsers)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/user/`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeUsers);

    httpMock.verify();
  });

  it('should ban user', () => {
    service.banUser(1).subscribe(x => x);

    const request = httpMock.expectOne(`${environment.apiUrl}/user/ban`);

    expect(request.request.method).toEqual('PUT');

    httpMock.verify();
  });

  it('should cancel meme', () => {
    service.cancelMeme(1).subscribe(x => x);

    const request = httpMock.expectOne(`${environment.apiUrl}/meme/cancel`);

    expect(request.request.method).toEqual('PUT');

    httpMock.verify();
  });
});
