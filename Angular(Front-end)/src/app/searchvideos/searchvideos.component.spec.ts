import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchvideosComponent } from './searchvideos.component';

describe('SearchvideosComponent', () => {
  let component: SearchvideosComponent;
  let fixture: ComponentFixture<SearchvideosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchvideosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchvideosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
