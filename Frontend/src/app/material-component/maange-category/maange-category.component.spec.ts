import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaangeCategoryComponent } from './maange-category.component';

describe('MaangeCategoryComponent', () => {
  let component: MaangeCategoryComponent;
  let fixture: ComponentFixture<MaangeCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MaangeCategoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MaangeCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
