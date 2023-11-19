import { IPatient } from 'app/shared/model/patient.model';
import { IMedecin } from 'app/shared/model/medecin.model';

export interface IEtablissement {
  id?: number;
  nom?: string | null;
  adresse?: string | null;
  telephone?: string | null;
  patients?: IPatient[] | null;
  medecins?: IMedecin[] | null;
}

export const defaultValue: Readonly<IEtablissement> = {};
