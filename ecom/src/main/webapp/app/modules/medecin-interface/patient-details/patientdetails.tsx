import React from 'react';
import './patientdetails.css';
import { useLocation } from 'react-router-dom';
import { PatientData } from '../classes/patient-class';
import PatientPreview from '../patient-preview/patientpreview';


export const VisualisationPatientDetail = () => {
  const location = useLocation();
  const patient: PatientData | undefined = location.state as PatientData;

  return (
    <PatientPreview
        patient = {patient}
    />
  );
};

export default VisualisationPatientDetail;
