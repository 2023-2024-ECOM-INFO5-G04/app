import React from "react"
import './patientslist.css'
import { Link } from "react-router-dom"
import { useState } from "react"


export const PatientsList = (props) => {

    const getColor = (denutrition) => {
        if (denutrition != null) {
            return '#C25E5E'
        }
        return '#5EC286'
    }

    // const [patients, setPatients] = props.patients
    const patients = props.patients

    return (
        <div className='patients-list-scrollable'>

        <ul>
            {patients.map((patient)=>
                <div key={patient.id}>
                    <Link to='/patientdetails' 
                    state={patient}>
                        Voir les détails du patient
                    </Link>
                    <button onClick={()=>alert(patient.id)}
                    className="patient-summary"
                    style={{ backgroundColor: getColor(patient.alert) }}
                    >
                        {patient.nom}
                    </button>
                </div>)
            } 

        </ul> 

        </div>

    )
}


export default PatientsList;