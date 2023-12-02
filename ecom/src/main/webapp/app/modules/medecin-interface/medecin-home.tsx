import React, { useState, useEffect } from 'react';
import PatientsList from './patients-list/patientslist';
import SelectionPatient from './patient-selection/patientselection';
import axios from 'axios';
import './medecinhome.css';
import donnesPatient from './classes/patient-class';

export const VisualisationPage = () => {
  const [patientData, setPatientData] = useState(null);
  const [requeteEffectuee, setRequeteEffectuee] = useState(false);

  let resp;

  useEffect(() => {
    if (!requeteEffectuee) {
      axios
        .get('api/patients')
        .then(response => {
          console.log("Réponse de l'API :", response.data);
          resp = donnesPatient(response.data);
        })
        .then(patientData => setPatientData(resp))
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

  if (patientData === null) {
    return <div>Chargement en cours...</div>;
  }

  return (
    <div>
      <h1> Visualisation des données patients</h1>
      <div className="medecin-home">
        <SelectionPatient patients={patientData} />
      </div>
    </div>
  );
};

export default VisualisationPage;
