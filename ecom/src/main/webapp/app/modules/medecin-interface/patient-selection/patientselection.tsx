import React from "react"
import { useState } from "react"
import './patientselection.css'
import Filtre from "../filter/filter";


const patients : string [] = ['Clément', 'Jamile', 'Mathis', 'Michelle', 'Léa'];
const etablissements : string [] = ['Polytech', 'Phelma', 'E3', 'IAE', 'Pagora'];

export const SelectionPatient = () => {

    const [selectedPatient, setSelectedPatient] = useState<string>('');
    const [selectedEtablissement, setSelectedEtablissement] = useState<string>(''); 

    return (
    // <div className='ecom-selection'> 
    <div className='patient-selection'> 
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

        <Filtre/>
        </div>

        
    )
}

export default SelectionPatient;