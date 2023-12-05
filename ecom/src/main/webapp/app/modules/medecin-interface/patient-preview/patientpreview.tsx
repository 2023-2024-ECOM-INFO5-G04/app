import React from 'react';
import { useState } from 'react';
import { Card, CardTitle, CardSubtitle, CardText, Button } from 'reactstrap';
import './patientpreview.css'
import { Note } from '../patient-preview/note';

export const PatientPreview = props => {
  const patient = props.patient;
  

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

      <Note patient={patient} />
    </div>
  );
};

export default PatientPreview;
