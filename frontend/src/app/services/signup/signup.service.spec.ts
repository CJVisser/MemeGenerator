import { TestBed } from '@angular/core/testing';
import { SignupService } from './signup.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { User } from '../../../app/models/User';
import { environment } from '../../../environments/environment';

describe('SignupService', () => {
  let service: SignupService;

  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(SignupService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should signup', () => {

    const fakeUser: User = {
        email: 'test',
        password: 'test',
        username: 'test',
        id: 1
    } 

    service.signup(fakeUser).subscribe((c)=>{
      expect(c).toEqual(fakeUser)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/user`);

    expect(request.request.method).toEqual('POST');

    request.flush(fakeUser);

    httpMock.verify();
  });
});
