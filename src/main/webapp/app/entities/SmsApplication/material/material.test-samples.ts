import dayjs from 'dayjs/esm';

import { Type } from 'app/entities/enumerations/type.model';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 89389,
};

export const sampleWithPartialData: IMaterial = {
  id: 63837,
  materialName: 'Licensed Operations Bedfordshire',
  createName: 'Borders user-centric Rubber',
  createDate: dayjs('2023-05-14T15:01'),
  delFlag: 7104,
};

export const sampleWithFullData: IMaterial = {
  id: 54378,
  materialName: 'methodologies Bedfordshire fuchsia',
  type: Type['ShortMessageService'],
  updateBy: 'USB overriding Sleek',
  updateName: 'withdrawal hacking',
  createBy: 'magnetic payment',
  createName: 'SMTP',
  updateDate: dayjs('2023-05-14T10:38'),
  createDate: dayjs('2023-05-15T01:42'),
  sort: 63040,
  delFlag: 60229,
};

export const sampleWithNewData: NewMaterial = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
