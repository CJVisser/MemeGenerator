import { ComponentFixture, getTestBed, inject, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { CreateComponent } from './create.component';
import { LoginService } from '../../../app/services/login/loginService';
import { environment } from '../../../environments/environment';
import { MemeService } from '../../../app/services/meme/memeService';

describe('CreateComponent', () => {
  let component: CreateComponent;
  let fixture: ComponentFixture<CreateComponent>;
  let mockLoginService;
  let mockMemeService;
  let mockHttp;
  let injector: TestBed;

  beforeEach(async () => {
    mockLoginService = jasmine.createSpyObj(['getCurrentUser']);
    mockLoginService.getCurrentUser.and.returnValue({points: 0});

    mockMemeService = jasmine.createSpyObj(['CreateMeme']);
    mockMemeService.CreateMeme.and.returnValue({subscribe: () => {}});

    await TestBed.configureTestingModule({
      declarations: [ CreateComponent ],
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [{provide: LoginService, useValue: mockLoginService}, {provide: MemeService, useValue: mockMemeService}]
    })
    .compileComponents();

    injector = getTestBed();
    mockHttp = injector.get(HttpTestingController);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set images', () => {
    component.images = [];

    expect(component.images).toEqual([]);

    component.setImages();

    expect(component.images).toEqual([
      {
        name: "Magikarp",
        url: "assets/magikarp.jpg"
      },
      {
        name: "Surprised Pikachu",
        url: "assets/pikachu.jpg"
      },
      {
        name: "SpongeBob 1",
        url: "assets/spongebob.jpg"
      },
      {
        name: "SpongeBob 2",
        url: "assets/spongebob2.jpg"
      }
    ]);
  })

  it('should get categories onInit', () => {
    const req = mockHttp.expectOne(`${environment.apiUrl}/category/`);
    expect(req.request.method).toBe("GET");
  })

  it('should get tags onInit', () => {
    const req = mockHttp.expectOne(`${environment.apiUrl}/tag/`);
    expect(req.request.method).toBe("GET");
  })

  it('should add tag', () => {
    component.tags = [{id: 1, title: 'test'}]

    expect(component.chosenTags).toEqual([]);

    component.addTag(1);

    expect(component.chosenTags).toEqual([{id: 1, title: 'test'}]);
  })

  it('should not add meme when empty title', () => {
    component.title = "";
    expect(mockMemeService.CreateMeme).not.toHaveBeenCalled();

    component.createMeme();

    expect(mockMemeService.CreateMeme).not.toHaveBeenCalled();
  })

  it('should not add meme when no category id', () => {
    component.chosenCategoryId = null;
    expect(mockMemeService.CreateMeme).not.toHaveBeenCalled();

    component.createMeme();

    expect(mockMemeService.CreateMeme).not.toHaveBeenCalled();
  })

  it('should add custom tag', () => {
    component.customTag = 'testTag';

    expect(component.chosenTags).toEqual([]);

    component.addCustomTag();

    expect(component.chosenTags).toEqual([{id: 0, title: 'testTag'}]);
  })
});
