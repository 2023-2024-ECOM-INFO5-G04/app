import React from 'react';
import './patientdetails.css';
import { useLocation } from 'react-router-dom';
import { PatientData } from '../classes/patient-class';
import PatientPreview from '../patient-preview/patientpreview';
import { height } from '@fortawesome/free-solid-svg-icons/faCogs';
import { Col, Row } from 'reactstrap';


export const VisualisationPatientDetail = () => {
  const location = useLocation();
  const patient: PatientData | undefined = location.state as PatientData;

  return (
    <div style={{ height: '80dvh' }}>
      <Col md='3'className='recap'>
        <PatientPreview
          patient={patient}
        />
      </Col>
    </div>
  );
};

export default VisualisationPatientDetail;
