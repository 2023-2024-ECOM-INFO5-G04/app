import { ICompte } from 'app/shared/model/compte.model';

export interface IAdministrateur {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  compte?: ICompte | null;
}

export const defaultValue: Readonly<IAdministrateur> = {};
