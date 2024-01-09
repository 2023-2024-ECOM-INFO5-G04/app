import { IUser } from 'app/shared/model/user.model';
import { ITache } from 'app/shared/model/tache.model';
import { IRappel } from 'app/shared/model/rappel.model';
import { INotes } from 'app/shared/model/notes.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IEtablissement } from 'app/shared/model/etablissement.model';

export interface IMedecin {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  user?: IUser | null;
  taches?: ITache[] | null;
  alertes?: IRappel[] | null;
  notes?: INotes[] | null;
  patients?: IPatient[] | null;
  etablissements?: IEtablissement[] | null;
}

export const defaultValue: Readonly<IMedecin> = {};
