import { TestBed } from '@angular/core/testing';
import { LoginService } from './loginService';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { environment } from '../../../environments/environment';

describe('LoginService', () => {
  let service: LoginService;
  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(LoginService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should loggedIn true', () => {
    const fakeUser = {
        username: 'test',
        password: 'test'
    }

    service.login('test', 'test').subscribe((c)=>{
        expect(c).toEqual(true)
    });

    const request1 = httpMock.expectOne(`${environment.apiUrl}/login`);

    expect(request1.request.method).toEqual('POST');

    request1.flush({status: true, userId: 1});

    const request2 = httpMock.expectOne(`${environment.apiUrl}/user/1`);

    expect(request2.request.method).toEqual('GET');

    request2.flush(fakeUser);

    httpMock.verify();

    service.loggedIn().subscribe(x => expect(x).toEqual(true));
  });

  // it('should login throw error', () => {
  //   service.login('test', 'test').subscribe((c)=>{
  //       expect(c).toEqual(false)
  //   });

  //   const request1 = httpMock.expectOne(`${environment.apiUrl}/login`);

  //   expect(request1.request.method).toEqual('POST');

  //   request1.flush({status: false, userId: 0});

  //   httpMock.verify();

  //   service.loggedIn().subscribe(x => expect(x).toThrowError("Wrong username"));
  // }); UNSTABLE on the toThrowError function

  it('should login set currentUser', () => {
    const fakeUser = {
        username: 'test',
        password: 'test',
        email: 'test'
    }

    service.login('test', 'test').subscribe((c)=>{
        expect(c).toEqual(true)
    });

    const request1 = httpMock.expectOne(`${environment.apiUrl}/login`);

    expect(request1.request.method).toEqual('POST');

    request1.flush({status: true, userId: 1});

    const request2 = httpMock.expectOne(`${environment.apiUrl}/user/1`);

    expect(request2.request.method).toEqual('GET');

    request2.flush(fakeUser);

    httpMock.verify();

    service.currentUser().subscribe(x => expect(x).toEqual(fakeUser));
  });

  it('should login set currentUser for getCurrentUser', () => {
    const fakeUser = {
        username: 'test',
        password: 'test',
        email: 'test'
    }

    service.login('test', 'test').subscribe((c)=>{
        expect(c).toEqual(true)
    });

    const request1 = httpMock.expectOne(`${environment.apiUrl}/login`);

    expect(request1.request.method).toEqual('POST');

    request1.flush({status: true, userId: 1});

    const request2 = httpMock.expectOne(`${environment.apiUrl}/user/1`);

    expect(request2.request.method).toEqual('GET');

    request2.flush(fakeUser);

    httpMock.verify();

    expect(service.getCurrentUser()).toEqual(fakeUser);
  });

  it('should loggedIn true for getLoggedIn', () => {
    const fakeUser = {
        username: 'test',
        password: 'test'
    }

    service.login('test', 'test').subscribe((c)=>{
        expect(c).toEqual(true)
    });

    const request1 = httpMock.expectOne(`${environment.apiUrl}/login`);

    expect(request1.request.method).toEqual('POST');

    request1.flush({status: true, userId: 1});

    const request2 = httpMock.expectOne(`${environment.apiUrl}/user/1`);

    expect(request2.request.method).toEqual('GET');

    request2.flush(fakeUser);

    httpMock.verify();

    expect(service.getLoggedIn()).toEqual(true);
  });

  it('should logout user', () => {
    // service.logout();

    // expect(service.getLoggedIn()).toEqual(false);
    // expect(service.getCurrentUser()).toEqual(null);

    // Commented out need to find fix for the window reload looping
  });
});
