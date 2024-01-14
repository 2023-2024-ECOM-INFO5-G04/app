import { Type, plainToClass } from 'class-transformer';

import { PatientData } from './patient-class';
import { Etablissement } from './patient-class';

/* Utilisation de User à la place de Compte :
  Liste des variables dans le json :
  "login": "Login",
    "firstName": "Prénom",
    "lastName": "Nom",
    "email": "Email",
    "activated": "Activé",
    "deactivated": "Désactivé",
    "profiles": "Droits",
    "langKey": "Langue",
    "createdBy": "Créé par",
    "createdDate": "Créé le",
    "lastModifiedBy": "Modifié par",
    "lastModifiedDate": "Modifié le"
 */
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

export class NoteData {
  id: number = 0;
  commentaire: string = "";
  medecin: Medecin;
  patient: PatientData;
}

export function donneesNote(jsonObject){
    return plainToClass(NoteData, jsonObject);
}


export default donneesNote;
