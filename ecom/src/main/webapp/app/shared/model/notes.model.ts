import { IMedecin } from 'app/shared/model/medecin.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface INotes {
  id?: number;
  commentaire?: string | null;
  patient?: IMedecin | null;
  medecin?: IPatient | null;
}

export const defaultValue: Readonly<INotes> = {};
