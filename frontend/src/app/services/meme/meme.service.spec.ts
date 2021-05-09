import { TestBed } from '@angular/core/testing';
import { MemeService } from './memeService';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { Meme } from '../../../app/models/Meme';
import { environment } from '../../../environments/environment';

describe('MemeService', () => {
  let service: MemeService;

  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(MemeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should createMeme', () => {

    const fakeMeme: Meme = {
         categoryId: 1,
         title: 'test',
         userId: 1
    } 

    service.CreateMeme(fakeMeme).subscribe((c)=>{
      expect(c.body).toEqual(fakeMeme)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/meme/`);

    expect(request.request.method).toEqual('POST');

    request.flush(fakeMeme);

    httpMock.verify();
  });

  it('should flag meme', () => {
    const fakeMeme: Meme = {
         categoryId: 1,
         title: 'test',
         userId: 1
    } 

    service.FlagMeme(fakeMeme.id);

    const request = httpMock.expectOne(`${environment.apiUrl}/meme/flag`);

    expect(request.request.method).toEqual('POST');

    request.flush("");

    httpMock.verify();
  });

  // Test fetching all
  it('should get all memes', () => {

    const fakeMemes : Meme[] = []

    for(let i = 0; i < 5; i ++){
      const category: Meme = {
        title: `Test meme ${i}`,
        categoryId: i,
        userId: 1
      };

      fakeMemes.push(category)
    }

    service.GetAllMemes().subscribe((c)=>{
      expect(c).toEqual(fakeMemes)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/meme/`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeMemes);

    httpMock.verify();
  });

  it('should get meme by id', () => {

    const fakeMeme: Meme = {
        categoryId: 1,
        title: 'test',
        userId: 1
   } 

    service.getMeme(fakeMeme.id).subscribe((c)=>{
      expect(c).toEqual(fakeMeme)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/meme/${fakeMeme.id}`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeMeme);

    httpMock.verify();
  });

  it('should get all memes by category', () => {

    const fakeMemes : Meme[] = []

    for(let i = 0; i < 5; i ++){
      const category: Meme = {
        title: `Test meme ${i}`,
        categoryId: 1,
        userId: 1
      };

      fakeMemes.push(category)
    }

    service.GetAllMemesFilteredOnCategory(1).subscribe((c)=>{
      expect(c).toEqual(fakeMemes)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/meme/category/1`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeMemes);

    httpMock.verify();
  });

  it('should get all memes by tag', () => {

    const fakeMemes : Meme[] = []

    for(let i = 0; i < 5; i ++){
      const category: Meme = {
        title: `Test meme ${i}`,
        categoryId: 1,
        userId: 1
      };

      fakeMemes.push(category)
    }

    service.GetAllMemesFilteredOnTag(1).subscribe((c)=>{
      expect(c).toEqual(fakeMemes)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/meme/tags/1`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeMemes);

    httpMock.verify();
  });
});
