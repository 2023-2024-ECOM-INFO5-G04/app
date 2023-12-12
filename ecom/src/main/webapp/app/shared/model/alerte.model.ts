import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';

export interface IAlerte {
  id?: number;
  date?: string | null;
  commentaire?: string | null;
  severite?: boolean | null;
  patient?: IPatient;
}

export const defaultValue: Readonly<IAlerte> = {
  severite: false,
};
