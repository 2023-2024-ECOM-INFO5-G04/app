import { ISoignant } from 'app/shared/model/soignant.model';
import { IMedecin } from 'app/shared/model/medecin.model';
import { IAdministrateur } from 'app/shared/model/administrateur.model';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface ICompte {
  id?: number;
  nomutilisateur?: string | null;
  motdepasse?: string | null;
  mail?: string | null;
  role?: Role | null;
  soignant?: ISoignant | null;
  medecin?: IMedecin | null;
  administrateur?: IAdministrateur | null;
}

export const defaultValue: Readonly<ICompte> = {};
