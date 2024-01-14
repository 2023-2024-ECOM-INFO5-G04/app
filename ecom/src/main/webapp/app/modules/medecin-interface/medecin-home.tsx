import React, { useState, useEffect } from 'react';
import PatientsList from './patients-list/patientslist';
import SelectionPatient from './patient-selection/patientselection';
import axios from 'axios';
import './medecinhome.css';
import donneesPatient from './classes/patient-class';
import { useAppSelector } from 'app/config/store';
import { Alert } from 'reactstrap';

export const VisualisationPage = () => {
  const [patientData, setPatientData] = useState(null);
  const [requeteEffectuee, setRequeteEffectuee] = useState(false);
  const account = useAppSelector(state => state.authentication.account);
  const roles = account.authorities;
  const isMed = roles?.includes('ROLE_MEDECIN') ?? false;

  let resp;

  useEffect(() => {
    if (!requeteEffectuee) {
      axios
        .get('api/patients')
        .then(response => {
          console.log("Réponse de l'API :", response.data);
          resp = donneesPatient(response.data);
        })
        .then(patientData => setPatientData(resp))
        .catch(error => {
          if (error.response) {
            console.log('Erreur de réponse du serveur :', error.response.data);
            console.log('Statut de la réponse du serveur :', error.response.status);
          } else if (error.request) {
            console.log('Aucune réponse reçue du serveur');
          } else {
            console.log('Erreur de configuration de la requête :', error.message);
          }
          console.log('Erreur complète :', error.config);
        });
      setRequeteEffectuee(true);
    }

  }, [requeteEffectuee]);





  if (patientData === null) {
    return <div>Un problème est servenu, vous êtes vous identifié.e ?</div>;
  }

  return (
    <div style={{ height: '80vh' }}>
      <h1 className='title'> Rechercher un patient</h1>
      {isMed ? (<div className="medecin-home">
        <SelectionPatient patients={patientData} />
      </div>)
        :
        (<Alert color='info'>
          Seul le médecin peut consulter les donées des patients
        </Alert>)}


    </div>
  );
};

export default VisualisationPage;
