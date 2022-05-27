import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PagnotfoundComponent } from './pagnotfound.component';

describe('PagnotfoundComponent', () => {
  let component: PagnotfoundComponent;
  let fixture: ComponentFixture<PagnotfoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PagnotfoundComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PagnotfoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
