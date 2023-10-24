import { ICompte } from 'app/shared/model/compte.model';

export interface IAdmin {
  id?: number;
  idA?: number;
  nomA?: string | null;
  prenomA?: string | null;
  compte?: ICompte | null;
}

export const defaultValue: Readonly<IAdmin> = {};
