import dayjs from 'dayjs';
import { IAlerte } from 'app/shared/model/alerte.model';
import { INotes } from 'app/shared/model/notes.model';
import { IEtablissement } from 'app/shared/model/etablissement.model';
import { ISuividonnees } from 'app/shared/model/suividonnees.model';
import { ITache } from 'app/shared/model/tache.model';
import { IMedecin } from 'app/shared/model/medecin.model';
import { ISoignant } from 'app/shared/model/soignant.model';
import { Sexe } from 'app/shared/model/enumerations/sexe.model';

export interface IPatient {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
  age?: number | null;
  datearrivee?: string | null;
  poidsactuel?: number | null;
  albumine?: number | null;
  taille?: number | null;
  sexe?: Sexe | null;
  favori?: boolean | null;
  sarcopenie?: boolean | null;
  alerte?: IAlerte | null;
  notes?: INotes[] | null;
  etablissement?: IEtablissement;
  suividonnees?: ISuividonnees[] | null;
  taches?: ITache[] | null;
  medecins?: IMedecin[] | null;
  soignants?: ISoignant[] | null;
}

export const defaultValue: Readonly<IPatient> = {
  favori: false,
  sarcopenie: false,
};
