import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';
import { IServicesoignant } from 'app/shared/model/servicesoignant.model';
import { ISoignant } from 'app/shared/model/soignant.model';
import { IMedecin } from 'app/shared/model/medecin.model';

export interface ITache {
  id?: number;
  date?: string | null;
  commentaire?: string | null;
  effectuee?: boolean | null;
  patient?: IPatient;
  servicesoignant?: IServicesoignant | null;
  soignant?: ISoignant | null;
  medecin?: IMedecin | null;
}

export const defaultValue: Readonly<ITache> = {
  effectuee: false,
};
