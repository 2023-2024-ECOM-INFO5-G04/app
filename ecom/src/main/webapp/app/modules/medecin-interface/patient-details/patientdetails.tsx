import React from 'react';
import './patientdetails.css';
import { useLocation } from 'react-router-dom';
import { PatientData } from '../classes/patient-class';
import PatientPreview from '../patient-preview/patientpreview';

import { Alert, Col, Row } from 'reactstrap';
import { useState } from 'react';
import GraphWindow from '../container/graphwindow';
import Task from '../container/task';

import { useAppSelector } from 'app/config/store';



export const VisualisationPatientDetail = () => {
  const location = useLocation();
  const patient: PatientData | undefined = location.state as PatientData;
  const [fenetre, setFenetre] = useState(true);

  const account = useAppSelector(state => state.authentication.account);
  const roles = account.authorities;
  const isMed = roles?.includes('ROLE_MEDECIN') ?? false;

  const handleClickT = () => {
    setFenetre(true);
    console.log(fenetre);
  }

  const handleClickF = () => {
    setFenetre(false);
    console.log(fenetre);
  }

   const component1 = <GraphWindow
    handleClick={handleClickF}
    numPa={patient ? patient.id : null}
  /> ;


  const component2 = <Task
    handleClick={handleClickT}
    patient={patient}/>


  return (

    <div style={{ height: '80dvh' }}>

      {isMed ? (<Row style={{ height: '100%' }}
        id='entire'>
        <Col md='3' className='recap'>
          <PatientPreview
            patient={patient}
          />
        </Col>
        <Col md='9'>
          {fenetre ? component1 : component2}
        </Col>
      </Row>) : (<Alert color='info'>
        Seul le médecin peut consulter les donées des patients
      </Alert>)}

    </div>

  );

};

export default VisualisationPatientDetail;
