
import { TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs';
import { TagService } from './tag.service';
import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { environment } from "../../../environments/environment";
import { Tag } from '../../../app/models/Tag';

describe('TagService', () => {
  let service: TagService;

  let httpMock: HttpTestingController;

  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(TagService);
    httpMock = TestBed.get(HttpTestingController);
    httpClient = TestBed.get(HttpClient);
  });

  // Test creation
  it('should create a tag', () => {

    const tag: Tag = {
      id: 1,
      title: 'Test tag',
    };

    service.createTag(tag).subscribe((t) => {

      expect(t.body).toEqual(tag);
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/tag/create`);

    expect(request.request.method).toEqual('POST');

    request.flush(tag);

    httpMock.verify();
  });

  // Test fetching all
  it('should get all tags', () => {

    const fakeTags: Tag[] = []

    for (let i = 0; i < 5; i++) {
      const tag: Tag = {
        title: `Test tag ${i}`,
      };

      fakeTags.push(tag)
    }

    service.getTags().subscribe((t) => {

      expect(t.body).toEqual(fakeTags)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/tag/`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeTags);

    httpMock.verify();
  });
});
