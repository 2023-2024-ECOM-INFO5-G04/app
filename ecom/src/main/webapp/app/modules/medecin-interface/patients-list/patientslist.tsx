import React from "react"
import './patientslist.css'

export const PatientsList = (props) => {

    const patients = props.patients
    console.log('dans patientsList', patients)

    return (
        <div className='patients-list-scrollable'>

        <ul>
            {patients.map((patient)=>
                <div key={patient.id}>
                    <button onClick={()=>alert(patient.id)}
                    className="patient-summary">
                        {patient.nom}
                    </button>
                </div>)
            } 

        </ul> 

        </div>

    )
}


export default PatientsList;