import React, { useEffect, useState } from 'react';
import './patientslist.css';
import { Link } from 'react-router-dom';
import axios from 'axios';


export const PatientsList = props => {

  const getColor = (denutrition, severite) => {
    if (denutrition == null) {
      return '#5EC286';
    }
    if (severite) {
      return '#C25E5E';
    }
    return '#FAE282';
  };

  // #C25E5E rouge
  // #5EC286 vert
  // #FAE282 jaune

  const patients = props.patients;

  const [EPA, setEPA] = useState<{ [key: string]: number }>({});


  useEffect(() => {
    patients.map(patient => {
      console.log(patient);
      axios
        .get('api/patients/' + patient.id + '/epa')
        .then(response => {
          setEPA((previousEPA) => ({
            ...previousEPA,
            [patient.id.toString()]: response.data
          }))
        })
        .catch(error => {
          console.error('Erreur lors de la requête pour l EPA :', error);
          return null;
        });
    })
  }, []);


  return (
    <div className="patients-list-scrollable">
      <ul>
        {patients.map(patient => (
          <div key={patient.id}
            className='container-patient'
          >
            <Link className="patient-summary" to="/patientdetails" style={{ backgroundColor: getColor(patient.alerte.denutrition, patient.alerte.severite) }} state={patient}>
              {patient.nom}

            </Link>
            <div className='display-EPA'>
              {EPA && ("EPA : " + EPA[patient.id.toString()])}
            </div>
          </div>
        ))}
      </ul>
    </div>
  );
};

export default PatientsList;
