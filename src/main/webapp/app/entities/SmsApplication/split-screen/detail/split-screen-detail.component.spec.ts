import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SplitScreenDetailComponent } from './split-screen-detail.component';

describe('SplitScreen Management Detail Component', () => {
  let comp: SplitScreenDetailComponent;
  let fixture: ComponentFixture<SplitScreenDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SplitScreenDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ splitScreen: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SplitScreenDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SplitScreenDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load splitScreen on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.splitScreen).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
