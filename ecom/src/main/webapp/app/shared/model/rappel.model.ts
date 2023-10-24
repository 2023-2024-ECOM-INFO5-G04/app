import dayjs from 'dayjs';
import { IMedecin } from 'app/shared/model/medecin.model';

export interface IRappel {
  id?: number;
  date?: string | null;
  commentaire?: string | null;
  effectue?: boolean | null;
  medecin?: IMedecin | null;
}

export const defaultValue: Readonly<IRappel> = {
  effectue: false,
};
