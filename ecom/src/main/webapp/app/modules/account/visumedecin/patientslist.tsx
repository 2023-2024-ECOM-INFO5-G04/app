//TODO
//importer depuis csv et parser

import React from "react"
import { useState } from "react"
import './visumedecin.css'

import donnesPatient from "./donnespatient";



const patients : string [] = ['Clément', 'Jamile', 'Mathis', 'Michelle', 'Léa']
// const etablissements : string [] = ['Polytech', 'Phelma', 'E3', 'IAE', 'Pagora']

export const PatientsList = (props) => {

    
    // const [selectedPatient, setSelectedPatient] = useState<string>('');
    // const [selectedEtablissement, setSelectedEtablissement] = useState<string>(''); 
  

    return (
        <div className='ecom-affichage'>

        <ul>
            {patients.map((patient)=>
                <div key={patient}>
                    <button onClick={()=>console.log(donnesPatient(props.patient))}
                    className="patient-selction">
                        {patient}
                    </button>
                </div>)
            } 

        </ul> 

        </div>

    )
}


export default PatientsList;