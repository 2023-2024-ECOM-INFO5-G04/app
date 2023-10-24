import { ICompte } from 'app/shared/model/compte.model';
import { IServicesoignant } from 'app/shared/model/servicesoignant.model';
import { IPatient } from 'app/shared/model/patient.model';
import { ITache } from 'app/shared/model/tache.model';
import { SoignantMetier } from 'app/shared/model/enumerations/soignant-metier.model';

export interface ISoignant {
  id?: number;
  idS?: number;
  nomS?: string | null;
  prenomS?: string | null;
  metier?: SoignantMetier | null;
  compte?: ICompte | null;
  servicesoignant?: IServicesoignant;
  patients?: IPatient[] | null;
  taches?: ITache[] | null;
}

export const defaultValue: Readonly<ISoignant> = {};
