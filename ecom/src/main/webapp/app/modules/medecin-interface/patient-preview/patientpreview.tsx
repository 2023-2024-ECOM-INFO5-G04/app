import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import './patientpreview.css';
import { Note } from '../note/note';
import donneesNote from '../classes/medecin-class';

///TODO :
// une fois que la requete fonctionne, recuperer et traiter le json pour l'afficher dans la note
// + utiliser une requete pour la modifier

export const PatientPreview = props => {
  const patient = props.patient;
  const [requeteEffectuee, setRequeteEffectuee] = useState(false);
  const [NoteData, setNoteData] = useState(null);
  const url = 'api/notes?medecin=1&patient=1';

  let resp;

  useEffect(() => {
    if (!requeteEffectuee) {
      axios
        .get(url)
        .then(response => {
          console.log("Réponse de l'API :", response.data);
          resp = donneesNote(response.data);
          console.log('resp', resp);
        })
        .then(patientData => setNoteData(resp))
        .catch(error => {
          if (error.response) {
            // La requête a été effectuée, mais le serveur a répondu avec un code d'erreur
            console.log('Erreur de réponse du serveur :', error.response.data);
            console.log('Statut de la réponse du serveur :', error.response.status);
          } else if (error.request) {
            // La requête a été effectuée, mais aucune réponse n'a été reçue
            console.log('Aucune réponse reçue du serveur');
          } else {
            // Une erreur s'est produite lors de la configuration de la requête
            console.log('Erreur de configuration de la requête :', error.message);
          }
          console.log('Erreur complète :', error.config);
        });
    }
  }, [requeteEffectuee]);

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

      <Note 
      patient={patient} />
    </div>
  );
};

export default PatientPreview;
