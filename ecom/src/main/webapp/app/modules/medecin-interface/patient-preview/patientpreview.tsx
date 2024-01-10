import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import './patientpreview.css';
import { Note } from '../note/note';


///TODO :
//nouvelle requete pour recuperer les donnees de l'établissement

//recuperer l'ID du medecin avec les roles, pour le moment tout le temps le meme medecin

export const PatientPreview = props => {
  const patient = props.patient;
  const medecinID = '1'
  const patientId = patient.id.toString();
  const url = 'api/notes?medecin=' + medecinID + '&patient=' + patientId;


  const [donnees, setDonnees] = useState(null);

  useEffect(() => {
    const effectFunction = async () => {
      try {

        const reponse = await axios.get(url);
        const reponseData = reponse.data[0];
        ;
        setDonnees(reponseData);
      } catch (erreur) {
        console.error('Erreur lors de la requête :', erreur);
      }
    };

    effectFunction();
  }, []);

  return (
    <div className="preview">
      <div >
        <h1>
          {patient.nom} {patient.prenom}
        </h1>
        age : {patient.age} <br />
        poids : {patient.age} <br />
        taille : {patient.taille} <br />
        sexe : {patient.sexe} <br />
        albumine : {patient.albumine} <br />
        Admis dans l'établissement {patient.etablissement.id} ({patient.etablissement.nom}) <br />
        depuis le {patient.datearrivee} <br />à l'adresse {patient.etablissement.adresse} <br />
        joignable au {patient.etablissement.telephone} <br />
      </div>

      {donnees && (
        <Note
          patient={patient}
          note={donnees}
        />
      )}
    </div>
  );
};

export default PatientPreview;
