import { IUser } from 'app/shared/model/user.model';
import { IServicesoignant } from 'app/shared/model/servicesoignant.model';
import { IPatient } from 'app/shared/model/patient.model';
import { ITache } from 'app/shared/model/tache.model';
import { SoignantMetier } from 'app/shared/model/enumerations/soignant-metier.model';

export interface ISoignant {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  metier?: SoignantMetier | null;
  user?: IUser | null;
  servicesoignant?: IServicesoignant;
  patients?: IPatient[] | null;
  taches?: ITache[] | null;
}

export const defaultValue: Readonly<ISoignant> = {};
