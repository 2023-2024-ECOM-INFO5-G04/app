import React from 'react';
import { useState } from 'react';
import './patientselection.css';
import Filtre from '../filter/filter';
import PatientsList from '../patients-list/patientslist';
import { Etablissement, PatientData } from '../classes/patient-class';
import Patient from 'app/entities/patient/patient';

export const SelectionPatient = props => {
  const [selectedPatient, setSelectedPatient] = useState<string>('');
  //   const [selectedEtablissement, setSelectedEtablissement] = useState<Etablissement>();
  const [selectedEtablissement, setSelectedEtablissement] = useState<Etablissement>(new Etablissement());
  const [patients, setPatients] = useState<PatientData[]>(props.patients || []);

  const allPatients = props.patients;
  let etablissements: Etablissement[] = [];

  allPatients.map(patient => {
    if (!etablissements.includes(patient.etablissement)) {
      etablissements.push(patient.etablissement);
    }
  });
  let allEtablissements = [].concat(etablissements);

  const IDtoEtablissement = (Id: number) => {
    const etablissementTrouve = etablissements.find(etablissement => etablissement.id === Id);
    return etablissementTrouve || new Etablissement();
  };

  const updateSelectedEtablissment = (etablissementID: string) => {
    if (etablissementID == 'all') {
      setPatients(allPatients);
      setSelectedEtablissement(new Etablissement());
    } else {
      const etablissement: Etablissement = IDtoEtablissement(parseInt(etablissementID));
      setSelectedEtablissement(etablissement);
      updatePatientsByEtablissement(etablissement);
      console.log('E updated', etablissement);
    }
  };

  const updatePatientsByEtablissement = (etablissement: Etablissement) => {
    let list: Array<PatientData> = [];
    allPatients.map(patient => {
      if (patient.etablissement.id == etablissement.id) {
        list.push(patient);
      }
    });
    setPatients(list);
  };

  return (
    <div className="patient-selection">
      <div>
        <select value={selectedPatient} onChange={e => setSelectedPatient(e.target.value)}>
          {/* <option value="">--Nom patient--</option> */}
          {patients &&
            patients.map(patient => (
              <option key={patient.id} value={patient.nom}>
                {patient.nom}
              </option>
            ))}
        </select>
        <h4>You chose {selectedPatient}</h4>

        <select value={selectedEtablissement?.id || ''} onChange={e => updateSelectedEtablissment(e.target.value)}>
          <option value="">--Etablissement--</option>
          <option key={-1} value="all">
            ALL
          </option>
          {etablissements &&
            etablissements.map(etablissement => (
              <option key={etablissement.id} value={etablissement.id}>
                {etablissement.id}
              </option>
            ))}
        </select>
        <h4>You chose {selectedEtablissement.id}</h4>

        <Filtre />
      </div>
      <PatientsList patients={patients} />
    </div>
  );
};

export default SelectionPatient;
