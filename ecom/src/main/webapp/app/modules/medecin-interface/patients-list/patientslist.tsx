import React, { useEffect, useState } from 'react';
import './patientslist.css';
import { Link } from 'react-router-dom';
import axios from 'axios';


export const PatientsList = props => {
  const getColor = denutrition => {
    if (denutrition != null) {
      return '#C25E5E';
    }
    return '#5EC286';
  };

  const patients = props.patients;
  // let EPAarray : { [key: string]: number }= {};

  const [EPA, setEPA] = useState<{ [key: string]: number }>({});


  useEffect(() => {
    patients.map(patient => {
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
            <Link className="patient-summary" to="/patientdetails" style={{ backgroundColor: getColor(patient.alerte) }} state={patient}>
              {patient.nom}

            </Link>
            <div className='display-EPA'>
            EPA : {EPA && EPA[patient.id.toString()]}
            </div>
          </div>
        ))}
      </ul>
    </div>
  );
};

export default PatientsList;
