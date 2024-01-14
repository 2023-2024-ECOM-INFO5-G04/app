import React from 'react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import './patientpreview.css';
import { Note } from '../note/note';
import { useAppSelector } from 'app/config/store';
import { useSearchParams } from 'react-router-dom';

export const PatientPreview = props => {
  const patient = props.patient;
  const patientId = patient.id.toString();

  const authentication = useAppSelector(state => state.authentication);
  const userID = authentication ? authentication.account.id : null;
  console.log('userID', userID);

  const [url, setUrl] = useState('')


  const [donnees, setDonnees] = useState(null);

  useEffect(() => {
    axios
      .get('api/medecins/user/' + userID)
      .then(response => {
        setUrl('api/notes?medecin=' + response.data.id + '&patient=' + patientId)
      })
      .catch(error => {
        console.error('Erreur lors de la requête pour l EPA :', error);
        return null;
      });
  }, []);


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
  }, [url]);

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
        Admis dans l'établissement : {patient.etablissement.nom} <br />
        depuis le : {patient.datearrivee} <br />à l'adresse {patient.etablissement.adresse} <br />
        joignable au : {patient.etablissement.telephone} <br />
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
