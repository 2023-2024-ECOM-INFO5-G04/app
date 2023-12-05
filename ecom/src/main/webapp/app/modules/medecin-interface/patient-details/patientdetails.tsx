import React from 'react';
import './patientdetails.css'
import { useLocation } from 'react-router-dom';
import { PatientData } from '../classes/patient-class';

export const VisualisationPatientDetail = () => {
    const location = useLocation();
    const patient : PatientData | undefined = location.state as PatientData;

    return (
        <div>
            <h1>{patient.nom} {patient.prenom}</h1>
            age : {patient.age} <br/>
            poids : {patient.age} <br/>
            taille : {patient.taille} <br/>
            sexe : {patient.sexe} <br/>
            albumine : {patient.albumine} <br/>

            Admis dans l'établissement {patient.etablissement.id} ({patient.etablissement.nom}) <br/>
            depuis le {patient.datearrivee} <br/>
            à l'adresse {patient.etablissement.adresse} <br/>
            joignable au {patient.etablissement.telephone} <br/>

            {/* en dénutrition ? {patient.alerte.denutrition ? 'oui' : 'non'} */}
            {/* note : {patient.alerte.commentaire } */}

            

            
        </div>
    )
}

export default VisualisationPatientDetail;
