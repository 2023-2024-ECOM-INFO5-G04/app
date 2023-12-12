import React from 'react';
import { useState, useEffect } from 'react';
import './patientselection.css';
import PatientsList from '../patients-list/patientslist';
import { Etablissement, PatientData } from '../classes/patient-class';
import { FormGroup, Input } from 'reactstrap';

export const SelectionPatient = props => {
  const [selectedEtablissement, setSelectedEtablissement] = useState<Etablissement>(new Etablissement());
  const [patients, setPatients] = useState<PatientData[]>(props.patients || []);
  const [patientsByE, setPatientsByE] = useState<PatientData[]>(props.patients || []);
  const [patientsByN, setPatientsByN] = useState<PatientData[]>(props.patients || []);

  const [ESelector, setESelector] = useState<number>(0); // on a selectionné un etablissement
  const [NSelector, setNSelector] = useState<string>(''); // on a renseigné un nom
  const [ASelector, setASelector] = useState<boolean>(false); //on a trié par date d'arrivée


  useEffect(() => {
    updatePatientsByName(NSelector);
  }, [NSelector]);

  useEffect(() => {
    if (ESelector) {
      updatePatientsByEtablissement(selectedEtablissement);
    } else {
      setPatientsByE(allPatients);
    }
  }, [ESelector]);

  useEffect(() => {
    updatePatientsByArrival(ASelector);
  }, [ASelector]);

  useEffect(() => {
    let patientsTemp = patientsByE.filter(element => patientsByN.includes(element));
    if (ASelector) {
      patientsTemp = (trierParDate(patientsTemp))
    }
    setPatients(patientsTemp);
    // setPatients(patientsByE.filter(element => patientsByN.includes(element)));
  }, [patientsByE, patientsByN]);

  const allPatients = props.patients;
  let etablissements: Etablissement[] = [];

  allPatients.map(patient => {
    if (!etablissements.includes(patient.etablissement)) {
      etablissements.push(patient.etablissement);
    }
  });

  const IDtoEtablissement = (Id: number) => {
    const etablissementTrouve = etablissements.find(etablissement => etablissement.id === Id);
    return etablissementTrouve || new Etablissement();
  };

  const updateSelectedEtablissment = (etablissementID: string) => {
    if (etablissementID == 'all') {
      setSelectedEtablissement(new Etablissement());
      setESelector(0);
    } else {
      const etablissement: Etablissement = IDtoEtablissement(parseInt(etablissementID));
      setSelectedEtablissement(etablissement);
      setESelector(ESelector + 1);
    }
  };

  const updatePatientsByEtablissement = (etablissement: Etablissement) => {
    let list: Array<PatientData> = [];

    allPatients.map(patient => {
      if (patient.etablissement.id == etablissement.id) {
        list.push(patient);
      }
    });

    setPatientsByE(list);
  };

  const updatePatientsByName = (name: string) => {
    name = name.toLowerCase();

    let list: Array<PatientData> = [];

    allPatients.map(patient => {
      if (patient.nom.toLowerCase().includes(name)) {
        list.push(patient);
      }
    });
    setPatientsByN(list);
  };

  const updatePatientsByArrival = (trier: boolean) => {
    if (trier) {
      setPatients(trierParDate(patients));
    }
  };

  function trierParDate(data: PatientData[]): PatientData[] {
    const dataTriee = data
      .map(item => ({ ...item, date: new Date(item.datearrivee) }))
      .sort((a, b) => a.date.getTime() - b.date.getTime())
      .map(item => ({ ...item, date: item.date.toISOString().split('T')[0] }));

    return dataTriee;
  }

  return (
    <div className="patient-affichage">
      <div className="patient-selection">
        <label className="choice">
          Recherche par nom :
          <br />
          <input name="name" onChange={e => setNSelector(e.target.value)} />
        </label>
        <br />
        <label className="choice">
          Recherche par établissement :
          <br />
          <select value={selectedEtablissement?.id || ''} onChange={e => updateSelectedEtablissment(e.target.value)}>
            <option value="">--Etablissement--</option>
            <option key={-1} value="all">
              ALL
            </option >
            {etablissements &&
              etablissements.map(etablissement => (
                <option key={etablissement.id} value={etablissement.id}>
                  {etablissement.id}
                </option>
              ))}
          </select>

        </label>
        <br />
        <label className='choice'>
          Trier : du dernier au premier arrivé
          <FormGroup switch>
            <Input
              style={{ width: '3em' }}
              type="switch"
              checked={ASelector}
              onChange={() => setASelector(!ASelector)}
            />
          </FormGroup>
        </label>


      </div>
      <PatientsList patients={patients} />
    </div>
  );
};

export default SelectionPatient;
