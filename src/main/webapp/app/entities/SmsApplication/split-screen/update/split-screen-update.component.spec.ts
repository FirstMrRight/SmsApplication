import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SplitScreenFormService } from './split-screen-form.service';
import { SplitScreenService } from '../service/split-screen.service';
import { ISplitScreen } from '../split-screen.model';

import { SplitScreenUpdateComponent } from './split-screen-update.component';

describe('SplitScreen Management Update Component', () => {
  let comp: SplitScreenUpdateComponent;
  let fixture: ComponentFixture<SplitScreenUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let splitScreenFormService: SplitScreenFormService;
  let splitScreenService: SplitScreenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SplitScreenUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SplitScreenUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SplitScreenUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    splitScreenFormService = TestBed.inject(SplitScreenFormService);
    splitScreenService = TestBed.inject(SplitScreenService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const splitScreen: ISplitScreen = { id: 456 };

      activatedRoute.data = of({ splitScreen });
      comp.ngOnInit();

      expect(comp.splitScreen).toEqual(splitScreen);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISplitScreen>>();
      const splitScreen = { id: 123 };
      jest.spyOn(splitScreenFormService, 'getSplitScreen').mockReturnValue(splitScreen);
      jest.spyOn(splitScreenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ splitScreen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: splitScreen }));
      saveSubject.complete();

      // THEN
      expect(splitScreenFormService.getSplitScreen).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(splitScreenService.update).toHaveBeenCalledWith(expect.objectContaining(splitScreen));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISplitScreen>>();
      const splitScreen = { id: 123 };
      jest.spyOn(splitScreenFormService, 'getSplitScreen').mockReturnValue({ id: null });
      jest.spyOn(splitScreenService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ splitScreen: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: splitScreen }));
      saveSubject.complete();

      // THEN
      expect(splitScreenFormService.getSplitScreen).toHaveBeenCalled();
      expect(splitScreenService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISplitScreen>>();
      const splitScreen = { id: 123 };
      jest.spyOn(splitScreenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ splitScreen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(splitScreenService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
