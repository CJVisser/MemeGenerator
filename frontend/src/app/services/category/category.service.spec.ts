import { TestBed } from '@angular/core/testing';
import { CategoryService } from './category.service';
import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { environment } from "environments/environment";
import { Category } from 'src/app/models/category';

describe('CategoryService', () => {
  let service: CategoryService;

  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(CategoryService);
    httpMock = TestBed.get(HttpTestingController);
  });

  // Test fetching all
  it('should get all categories', () => {

    const fakeCategories : Category[] = []

    for(let i = 0; i < 5; i ++){
      const category: Category = {
        title: `Test category ${i}`
      };

      fakeCategories.push(category)
    }

    service.getCategories().subscribe((c)=>{
      expect(c).toEqual(fakeCategories)
    });

    const request = httpMock.expectOne(`${environment.apiUrl}/category/`);

    expect(request.request.method).toEqual('GET');

    request.flush(fakeCategories);

    httpMock.verify();
  });
});
