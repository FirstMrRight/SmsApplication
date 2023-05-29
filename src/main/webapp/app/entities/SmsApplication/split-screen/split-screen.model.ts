import dayjs from 'dayjs/esm';

export interface ISplitScreen {
  id: number;
  materialId?: string | null;
  content?: string | null;
  pictureUrl?: string | null;
  pictureSize?: number | null;
  updateBy?: string | null;
  updateName?: string | null;
  createBy?: string | null;
  createName?: string | null;
  updateDate?: dayjs.Dayjs | null;
  createDate?: dayjs.Dayjs | null;
  sequence?: number | null;
  delFlag?: number | null;
}

export type NewSplitScreen = Omit<ISplitScreen, 'id'> & { id: null };
