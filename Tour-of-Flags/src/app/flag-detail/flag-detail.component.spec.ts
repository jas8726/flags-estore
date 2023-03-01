import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlagDetailComponent } from './flag-detail.component';

describe('FlagDetailComponent', () => {
  let component: FlagDetailComponent;
  let fixture: ComponentFixture<FlagDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlagDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlagDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
