import React from 'react';
import './patientdetails.css';
import { useLocation } from 'react-router-dom';
import { PatientData } from '../classes/patient-class';
import PatientPreview from '../patient-preview/patientpreview';
import { height } from '@fortawesome/free-solid-svg-icons/faCogs';
import { Col, Row } from 'reactstrap';
import LineChart from "app/modules/medecin-interface/graphe/graphe";


export const VisualisationPatientDetail = () => {
  const location = useLocation();
  const patient: PatientData | undefined = location.state as PatientData;

  return (
    <div style={{ display: 'flex', height: '80dvh' }}>
      <Col md='3'className='recap'>
        <PatientPreview
          patient={patient}
        />
      </Col>
      <div style={{ flex: 1 }}>
        <LineChart />
      </div>
    </div>
  );
};

export default VisualisationPatientDetail;
