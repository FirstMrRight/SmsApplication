import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISplitScreen, NewSplitScreen } from '../split-screen.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISplitScreen for edit and NewSplitScreenFormGroupInput for create.
 */
type SplitScreenFormGroupInput = ISplitScreen | PartialWithRequiredKeyOf<NewSplitScreen>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISplitScreen | NewSplitScreen> = Omit<T, 'updateDate' | 'createDate'> & {
  updateDate?: string | null;
  createDate?: string | null;
};

type SplitScreenFormRawValue = FormValueOf<ISplitScreen>;

type NewSplitScreenFormRawValue = FormValueOf<NewSplitScreen>;

type SplitScreenFormDefaults = Pick<NewSplitScreen, 'id' | 'updateDate' | 'createDate'>;

type SplitScreenFormGroupContent = {
  id: FormControl<SplitScreenFormRawValue['id'] | NewSplitScreen['id']>;
  materialId: FormControl<SplitScreenFormRawValue['materialId']>;
  content: FormControl<SplitScreenFormRawValue['content']>;
  pictureUrl: FormControl<SplitScreenFormRawValue['pictureUrl']>;
  pictureSize: FormControl<SplitScreenFormRawValue['pictureSize']>;
  updateBy: FormControl<SplitScreenFormRawValue['updateBy']>;
  updateName: FormControl<SplitScreenFormRawValue['updateName']>;
  createBy: FormControl<SplitScreenFormRawValue['createBy']>;
  createName: FormControl<SplitScreenFormRawValue['createName']>;
  updateDate: FormControl<SplitScreenFormRawValue['updateDate']>;
  createDate: FormControl<SplitScreenFormRawValue['createDate']>;
  sequence: FormControl<SplitScreenFormRawValue['sequence']>;
  delFlag: FormControl<SplitScreenFormRawValue['delFlag']>;
};

export type SplitScreenFormGroup = FormGroup<SplitScreenFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SplitScreenFormService {
  createSplitScreenFormGroup(splitScreen: SplitScreenFormGroupInput = { id: null }): SplitScreenFormGroup {
    const splitScreenRawValue = this.convertSplitScreenToSplitScreenRawValue({
      ...this.getFormDefaults(),
      ...splitScreen,
    });
    return new FormGroup<SplitScreenFormGroupContent>({
      id: new FormControl(
        { value: splitScreenRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      materialId: new FormControl(splitScreenRawValue.materialId),
      content: new FormControl(splitScreenRawValue.content),
      pictureUrl: new FormControl(splitScreenRawValue.pictureUrl),
      pictureSize: new FormControl(splitScreenRawValue.pictureSize),
      updateBy: new FormControl(splitScreenRawValue.updateBy),
      updateName: new FormControl(splitScreenRawValue.updateName),
      createBy: new FormControl(splitScreenRawValue.createBy),
      createName: new FormControl(splitScreenRawValue.createName),
      updateDate: new FormControl(splitScreenRawValue.updateDate),
      createDate: new FormControl(splitScreenRawValue.createDate),
      sequence: new FormControl(splitScreenRawValue.sequence),
      delFlag: new FormControl(splitScreenRawValue.delFlag),
    });
  }

  getSplitScreen(form: SplitScreenFormGroup): ISplitScreen | NewSplitScreen {
    return this.convertSplitScreenRawValueToSplitScreen(form.getRawValue() as SplitScreenFormRawValue | NewSplitScreenFormRawValue);
  }

  resetForm(form: SplitScreenFormGroup, splitScreen: SplitScreenFormGroupInput): void {
    const splitScreenRawValue = this.convertSplitScreenToSplitScreenRawValue({ ...this.getFormDefaults(), ...splitScreen });
    form.reset(
      {
        ...splitScreenRawValue,
        id: { value: splitScreenRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SplitScreenFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      updateDate: currentTime,
      createDate: currentTime,
    };
  }

  private convertSplitScreenRawValueToSplitScreen(
    rawSplitScreen: SplitScreenFormRawValue | NewSplitScreenFormRawValue
  ): ISplitScreen | NewSplitScreen {
    return {
      ...rawSplitScreen,
      updateDate: dayjs(rawSplitScreen.updateDate, DATE_TIME_FORMAT),
      createDate: dayjs(rawSplitScreen.createDate, DATE_TIME_FORMAT),
    };
  }

  private convertSplitScreenToSplitScreenRawValue(
    splitScreen: ISplitScreen | (Partial<NewSplitScreen> & SplitScreenFormDefaults)
  ): SplitScreenFormRawValue | PartialWithRequiredKeyOf<NewSplitScreenFormRawValue> {
    return {
      ...splitScreen,
      updateDate: splitScreen.updateDate ? splitScreen.updateDate.format(DATE_TIME_FORMAT) : undefined,
      createDate: splitScreen.createDate ? splitScreen.createDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
