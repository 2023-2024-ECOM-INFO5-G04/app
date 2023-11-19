//TODO
//importer depuis csv et parser

import React from "react"
import { useState } from "react"
import './visumedecin.css'



const patients : string [] = ['Clément', 'Jamile', 'Mathis', 'Michelle', 'Léa']
const etablissements : string [] = ['Polytech', 'Phelma', 'E3', 'IAE', 'Pagora']

export const PatientsList = (props) => {

    
    const [selectedPatient, setSelectedPatient] = useState<string>('');
    const [selectedEtablissement, setSelectedEtablissement] = useState<string>(''); 
  

    return (
    <div className='ecom-visu-global'>
        
        <div className='ecom-selection'> 
        <select 
            value = {selectedPatient} 
            onChange={(e) => setSelectedPatient(e.target.value)}
            >
            <option value=''>--Nom patient--</option>
            {patients.map((patient)=>
            
                <option key= {patient + "1"} value={patient}> {patient} </option>
            )}
   		</select>
    	<h4>You chose {selectedPatient}</h4>

        <select 
            value = {selectedEtablissement} 
            onChange={(e) => setSelectedEtablissement(e.target.value)}
            >
            <option value=''>--Etablissement--</option>
            {etablissements.map((etablissement)=>
            
                <option key= {etablissement} value={etablissement}> {etablissement} </option>
            )}
   		</select>
    	<h4>You chose {selectedEtablissement}</h4>

        <button onClick={() => alert('not implemeted yet')}> Filtres </button>
        </div>

        <div className='ecom-affichage'>

        <ul>
            {patients.map((patient)=>
                <div key={patient}>
                    <button onClick={()=>alert({patient} + 'a été selectionné')}
                    className="patient-selction">
                        {patient}
                    </button>
                </div>)
            } 

        </ul>

        </div>

    </div>

    )
}


export default PatientsList;