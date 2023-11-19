// TODO
//deplacer le dossier à un endroit plus approprié


import React, { useState, useEffect } from 'react';
import PatientDetail from '../../../entities/patient/patient-detail';
import Patient from '../../../entities/patient/patient';
import PatientList from './patientslist';




export const VisualisationPage = () => {



    return (
        <div>
            <h1> Visualisation des données patients</h1>
            <PatientList/>
        </div>

    )
}

export default VisualisationPage;