
import React from 'react';
 
import { plainToClass } from "class-transformer"; 

class PatientData { 
    id: number; 
    nom: string; 
    prenom: string; 
    age: number; 
    datearrivee: string;
    poidsactuel: number;
    albumine: number;
    taille: number;
    sexe: string
    alerte: Alerte;
    etablissement: Etablissement;
}

class Alerte {
  id: number;
  date: string;
  commentaire: string;
  denutrition: boolean;
}

class Etablissement {
  id: number;
  nom: string;
  adresse: string;
  telephone: string;
}

export function donnesPatient(jsonObject) {
    
    return(plainToClass(PatientData, jsonObject))

}

export default donnesPatient


