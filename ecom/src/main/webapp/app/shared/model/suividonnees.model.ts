import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';

export interface ISuividonnees {
  id?: number;
  date?: string;
  poids?: number | null;
  epa?: number | null;
  massecorporelle?: number | null;
  quantitepoidsaliments?: number | null;
  quantitecaloriesaliments?: number | null;
  absorptionreduite?: boolean | null;
  agression?: boolean | null;
  patient?: IPatient;
}

export const defaultValue: Readonly<ISuividonnees> = {
  absorptionreduite: false,
  agression: false,
};
