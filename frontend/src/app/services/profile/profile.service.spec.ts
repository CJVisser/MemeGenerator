import { TestBed } from '@angular/core/testing';
import { ProfileService } from './profile.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { User } from '../../../app/models/User';
import { environment } from '../../../environments/environment';

describe('ProfileService', () => {
  let service: ProfileService;

  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(ProfileService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should get user', () => {

    const fakeUser: User = {
        email: 'test',
        password: 'test',
        username: 'test',
        id: 1
    } 

    service.getUserInfo(fakeUser.id).subscribe((c)=>{
      expect(c).toEqual(fakeUser)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/user/${fakeUser.id}`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeUser);

    httpMock.verify();
  });

  it('should update user', () => {
    const fakeUser: User = {
        email: 'test',
        password: 'test',
        username: 'test'
    } 

    service.updateUserInfo(fakeUser).subscribe((c)=>{
      expect(c).toEqual(fakeUser)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/user`);

    expect(request.request.method).toEqual('PUT');

    request.flush(fakeUser);

    httpMock.verify();
  });
});
