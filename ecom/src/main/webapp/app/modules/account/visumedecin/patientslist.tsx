//TODO
//importer depuis csv et parser

import React from "react"
import { useState } from "react"
import './visumedecin.css'

import donnesPatient from "./donnespatient";



// const patients : string [] = ['Clément', 'Jamile', 'Mathis', 'Michelle', 'Léa']
// const etablissements : string [] = ['Polytech', 'Phelma', 'E3', 'IAE', 'Pagora']

export const PatientsList = (props) => {

    const patients = props.patients
    console.log('dans patientsList', patients)
    // const [selectedPatient, setSelectedPatient] = useState<string>('');
    // const [selectedEtablissement, setSelectedEtablissement] = useState<string>(''); 
  

    return (
        <div className='ecom-affichage-scrollable'>

        <ul>
            {patients.map((patient)=>
                <div key={patient.id}>
                    <button onClick={()=>alert(patient.id)}
                    className="patient-selction">
                        {patient.nom}
                    </button>
                </div>)
            } 

        </ul> 

        </div>

    )
}


export default PatientsList;