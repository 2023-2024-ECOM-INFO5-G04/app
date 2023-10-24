import { IEtablissement } from 'app/shared/model/etablissement.model';
import { ISoignant } from 'app/shared/model/soignant.model';
import { ITache } from 'app/shared/model/tache.model';

export interface IServicesoignant {
  id?: number;
  idSer?: number;
  type?: string | null;
  nbsoignants?: string | null;
  infrastructure?: IEtablissement;
  soignants?: ISoignant[] | null;
  taches?: ITache[] | null;
}

export const defaultValue: Readonly<IServicesoignant> = {};
