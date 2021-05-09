import { TestBed } from '@angular/core/testing';
import { SearchService } from './search.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';

describe('SearchService', () => {
  let service: SearchService;
  let httpMock : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(SearchService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should emit updateText', () => {
    spyOn(service.updatetSearch, 'emit');

    service.searchDone('test');

    expect(service.updatetSearch.emit).toHaveBeenCalledWith('test');
  });

  it('should emit updatetCategory', () => {
    spyOn(service.updatetCategory, 'emit');

    service.categoryChanged('test2');

    expect(service.updatetCategory.emit).toHaveBeenCalledWith('test2');
  });
});
