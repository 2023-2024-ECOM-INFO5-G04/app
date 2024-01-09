import { IUser } from 'app/shared/model/user.model';

export interface IAdministrateur {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IAdministrateur> = {};
