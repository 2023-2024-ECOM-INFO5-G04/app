import { IMedecin } from 'app/shared/model/medecin.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface INotes {
  id?: number;
  commentaire?: string | null;
  medecin?: IMedecin | null;
  patient?: IPatient | null;
}

export const defaultValue: Readonly<INotes> = {};
