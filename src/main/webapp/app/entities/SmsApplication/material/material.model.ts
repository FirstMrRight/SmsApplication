import dayjs from 'dayjs/esm';
import { Type } from 'app/entities/enumerations/type.model';

export interface IMaterial {
  id: number;
  materialName?: string | null;
  type?: Type | null;
  updateBy?: string | null;
  updateName?: string | null;
  createBy?: string | null;
  createName?: string | null;
  updateDate?: dayjs.Dayjs | null;
  createDate?: dayjs.Dayjs | null;
  sort?: number | null;
  delFlag?: number | null;
}

export type NewMaterial = Omit<IMaterial, 'id'> & { id: null };
