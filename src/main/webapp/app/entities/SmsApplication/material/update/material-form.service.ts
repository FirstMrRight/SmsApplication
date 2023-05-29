import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMaterial, NewMaterial } from '../material.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMaterial for edit and NewMaterialFormGroupInput for create.
 */
type MaterialFormGroupInput = IMaterial | PartialWithRequiredKeyOf<NewMaterial>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMaterial | NewMaterial> = Omit<T, 'updateDate' | 'createDate'> & {
  updateDate?: string | null;
  createDate?: string | null;
};

type MaterialFormRawValue = FormValueOf<IMaterial>;

type NewMaterialFormRawValue = FormValueOf<NewMaterial>;

type MaterialFormDefaults = Pick<NewMaterial, 'id' | 'updateDate' | 'createDate'>;

type MaterialFormGroupContent = {
  id: FormControl<MaterialFormRawValue['id'] | NewMaterial['id']>;
  materialName: FormControl<MaterialFormRawValue['materialName']>;
  type: FormControl<MaterialFormRawValue['type']>;
  updateBy: FormControl<MaterialFormRawValue['updateBy']>;
  updateName: FormControl<MaterialFormRawValue['updateName']>;
  createBy: FormControl<MaterialFormRawValue['createBy']>;
  createName: FormControl<MaterialFormRawValue['createName']>;
  updateDate: FormControl<MaterialFormRawValue['updateDate']>;
  createDate: FormControl<MaterialFormRawValue['createDate']>;
  sort: FormControl<MaterialFormRawValue['sort']>;
  delFlag: FormControl<MaterialFormRawValue['delFlag']>;
};

export type MaterialFormGroup = FormGroup<MaterialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MaterialFormService {
  createMaterialFormGroup(material: MaterialFormGroupInput = { id: null }): MaterialFormGroup {
    const materialRawValue = this.convertMaterialToMaterialRawValue({
      ...this.getFormDefaults(),
      ...material,
    });
    return new FormGroup<MaterialFormGroupContent>({
      id: new FormControl(
        { value: materialRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      materialName: new FormControl(materialRawValue.materialName),
      type: new FormControl(materialRawValue.type),
      updateBy: new FormControl(materialRawValue.updateBy),
      updateName: new FormControl(materialRawValue.updateName),
      createBy: new FormControl(materialRawValue.createBy),
      createName: new FormControl(materialRawValue.createName),
      updateDate: new FormControl(materialRawValue.updateDate),
      createDate: new FormControl(materialRawValue.createDate),
      sort: new FormControl(materialRawValue.sort),
      delFlag: new FormControl(materialRawValue.delFlag),
    });
  }

  getMaterial(form: MaterialFormGroup): IMaterial | NewMaterial {
    return this.convertMaterialRawValueToMaterial(form.getRawValue() as MaterialFormRawValue | NewMaterialFormRawValue);
  }

  resetForm(form: MaterialFormGroup, material: MaterialFormGroupInput): void {
    const materialRawValue = this.convertMaterialToMaterialRawValue({ ...this.getFormDefaults(), ...material });
    form.reset(
      {
        ...materialRawValue,
        id: { value: materialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MaterialFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      updateDate: currentTime,
      createDate: currentTime,
    };
  }

  private convertMaterialRawValueToMaterial(rawMaterial: MaterialFormRawValue | NewMaterialFormRawValue): IMaterial | NewMaterial {
    return {
      ...rawMaterial,
      updateDate: dayjs(rawMaterial.updateDate, DATE_TIME_FORMAT),
      createDate: dayjs(rawMaterial.createDate, DATE_TIME_FORMAT),
    };
  }

  private convertMaterialToMaterialRawValue(
    material: IMaterial | (Partial<NewMaterial> & MaterialFormDefaults)
  ): MaterialFormRawValue | PartialWithRequiredKeyOf<NewMaterialFormRawValue> {
    return {
      ...material,
      updateDate: material.updateDate ? material.updateDate.format(DATE_TIME_FORMAT) : undefined,
      createDate: material.createDate ? material.createDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
