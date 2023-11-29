import React from 'react';
import './patientdetails.css'
import { useLocation } from 'react-router-dom';
import { PatientData } from '../classes/patient-class';

export const VisualisationPatientDetail = () => {
    const location = useLocation();
    const patient : PatientData | undefined = location.state as PatientData;

    return (
        <div>
            {patient.nom}
        </div>
    )
}

export default VisualisationPatientDetail;