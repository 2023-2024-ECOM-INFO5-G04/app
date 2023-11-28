// TODO
//deplacer le dossier à un endroit plus approprié


import React, { useState, useEffect } from 'react';
import PatientDetail from '../../../entities/patient/patient-detail';
import Patient from '../../../entities/patient/patient';
import PatientsList from './patientslist';
import SelectionPatient from './selectionpatient';
import jsonObject from './patient.json';
import axios from 'axios';
import donnesPatient from './donnespatient';







export const VisualisationPage = () => {

  const [patientData, setPatientData] = useState(null);
  const [requeteEffectuee, setRequeteEffectuee] = useState(false);

  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       const response = await axios.get('https://api.example.com/data');
  //       const transformedData = response.data.map(data => PatientDataTransformer.transform(data));
  //       setPatientData(transformedData);
  //     } catch (error) {
  //       console.error('Erreur lors de la requête GET :', error);
  //     }
  //   };

  //   fetchData();
  // }, []);

  let resp;

  useEffect(() => {
    if (!requeteEffectuee) {
  axios.get('api/patients')
  .then(response => {
    console.log('Réponse de l\'API :', response.data);
    resp = donnesPatient(response.data);
    console.log('dans visuMedecin', resp);
  })
  .then(patientData =>setPatientData(resp))
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
  });}
}, [requeteEffectuee]); 

  if (patientData === null) {

    return <div>Chargement en cours...</div>;
  }

  

  

    return (
        <div>
            <h1> Visualisation des données patients</h1>
              <div className='ecom-visu-global'>
                <SelectionPatient
                  />
                <PatientsList
                  patients={patientData}/>
              </div>

        </div>

    )
}

export default VisualisationPage;