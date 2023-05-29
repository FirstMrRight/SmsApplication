import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../split-screen.test-samples';

import { SplitScreenFormService } from './split-screen-form.service';

describe('SplitScreen Form Service', () => {
  let service: SplitScreenFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SplitScreenFormService);
  });

  describe('Service methods', () => {
    describe('createSplitScreenFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSplitScreenFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            materialId: expect.any(Object),
            content: expect.any(Object),
            pictureUrl: expect.any(Object),
            pictureSize: expect.any(Object),
            updateBy: expect.any(Object),
            updateName: expect.any(Object),
            createBy: expect.any(Object),
            createName: expect.any(Object),
            updateDate: expect.any(Object),
            createDate: expect.any(Object),
            sequence: expect.any(Object),
            delFlag: expect.any(Object),
          })
        );
      });

      it('passing ISplitScreen should create a new form with FormGroup', () => {
        const formGroup = service.createSplitScreenFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            materialId: expect.any(Object),
            content: expect.any(Object),
            pictureUrl: expect.any(Object),
            pictureSize: expect.any(Object),
            updateBy: expect.any(Object),
            updateName: expect.any(Object),
            createBy: expect.any(Object),
            createName: expect.any(Object),
            updateDate: expect.any(Object),
            createDate: expect.any(Object),
            sequence: expect.any(Object),
            delFlag: expect.any(Object),
          })
        );
      });
    });

    describe('getSplitScreen', () => {
      it('should return NewSplitScreen for default SplitScreen initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSplitScreenFormGroup(sampleWithNewData);

        const splitScreen = service.getSplitScreen(formGroup) as any;

        expect(splitScreen).toMatchObject(sampleWithNewData);
      });

      it('should return NewSplitScreen for empty SplitScreen initial value', () => {
        const formGroup = service.createSplitScreenFormGroup();

        const splitScreen = service.getSplitScreen(formGroup) as any;

        expect(splitScreen).toMatchObject({});
      });

      it('should return ISplitScreen', () => {
        const formGroup = service.createSplitScreenFormGroup(sampleWithRequiredData);

        const splitScreen = service.getSplitScreen(formGroup) as any;

        expect(splitScreen).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISplitScreen should not enable id FormControl', () => {
        const formGroup = service.createSplitScreenFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSplitScreen should disable id FormControl', () => {
        const formGroup = service.createSplitScreenFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
