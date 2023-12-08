import { Type, plainToClass } from 'class-transformer';

import { PatientData } from './patient-class';
import { Etablissement } from './patient-class';


class Compte {
  id: number = 0;
  nomutilisateur: string = "";
  motdepasse: string = "";
  mail: string = "";
  role: string = "";
}

export class Medecin {
  id: number = 0;
  commentaire: string = "";
  nom: string = "";
  prenom: string = "";
  compte: Compte;
  patients: PatientData[];
  etablissements: Etablissement[];
}

class Note {
  id: number = 0;
  commentaire: string = "";
  medecin: Medecin;
  patient: PatientData;
}

export function donneesNote(jsonObject){
    return plainToClass(Note, jsonObject);
}


export default donneesNote;
