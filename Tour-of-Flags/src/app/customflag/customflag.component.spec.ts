import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomflagComponent } from './customflag.component';

describe('CustomflagComponent', () => {
  let component: CustomflagComponent;
  let fixture: ComponentFixture<CustomflagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomflagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomflagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
