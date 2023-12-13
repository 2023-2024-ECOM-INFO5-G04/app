import React, { useEffect } from 'react';
import './patientdetails.css';
import { Link, useLocation } from 'react-router-dom';
import { PatientData } from '../classes/patient-class';
import PatientPreview from '../patient-preview/patientpreview';
import { height } from '@fortawesome/free-solid-svg-icons/faCogs';
import { Button, Col, Row } from 'reactstrap';
import ReactDOM from 'react-dom/client';
import { useState } from 'react';
import GraphWindow from '../container/graphwindow';
import Task from '../container/task';


export const VisualisationPatientDetail = () => {
  const location = useLocation();
  const patient: PatientData | undefined = location.state as PatientData;
  const [fenetre, setFenetre] = useState(true);

  const handleClickT = () => {
    setFenetre(true);
    console.log(fenetre);
  }

  const handleClickF = () => {
    setFenetre(false);
    console.log(fenetre);
  }



 


  const component1 = <GraphWindow
    handleClick={handleClickF} />;

  const component2 = <Task
  handleClick={handleClickT}/>


  return (
    <div style={{ height: '80dvh' }}>
      <Row style={{ height: '100%' }}
        id='entire'>
        <Col md='3' className='recap'>
          <PatientPreview
            patient={patient}
          />
        </Col>
        {fenetre ? component1 : component2}

      </Row>
    </div>);
};

export default VisualisationPatientDetail;
