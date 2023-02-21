import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlagSearchComponent } from './flag-search.component';

describe('FlagSearchComponent', () => {
  let component: FlagSearchComponent;
  let fixture: ComponentFixture<FlagSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlagSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlagSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
