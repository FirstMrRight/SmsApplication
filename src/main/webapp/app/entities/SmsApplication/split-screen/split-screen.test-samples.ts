import dayjs from 'dayjs/esm';

import { ISplitScreen, NewSplitScreen } from './split-screen.model';

export const sampleWithRequiredData: ISplitScreen = {
  id: 51482,
};

export const sampleWithPartialData: ISplitScreen = {
  id: 24621,
  materialId: 'Account withdrawal magnetic',
  pictureUrl: 'systematic Sri leverage',
  updateName: 'encoding Card',
  createBy: 'encryption feed incubate',
  createName: 'Quality Handcrafted Unbranded',
  updateDate: dayjs('2023-05-15T07:39'),
  createDate: dayjs('2023-05-14T22:43'),
  delFlag: 49400,
};

export const sampleWithFullData: ISplitScreen = {
  id: 87564,
  materialId: 'explicit Chad',
  content: 'blockchains Integration',
  pictureUrl: 'payment optical',
  pictureSize: 57528,
  updateBy: 'programming',
  updateName: 'redundant',
  createBy: 'Concrete Pants Monitored',
  createName: '浙江省 Intelligent 桥',
  updateDate: dayjs('2023-05-14T23:18'),
  createDate: dayjs('2023-05-14T15:06'),
  sequence: 71676,
  delFlag: 96647,
};

export const sampleWithNewData: NewSplitScreen = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
