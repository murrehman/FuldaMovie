import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MostWaitedMoviesComponent } from './most-waited-movies.component';

describe('MostWaitedMoviesComponent', () => {
  let component: MostWaitedMoviesComponent;
  let fixture: ComponentFixture<MostWaitedMoviesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MostWaitedMoviesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MostWaitedMoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
