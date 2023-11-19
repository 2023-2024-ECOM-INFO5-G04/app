// TODO
//deplacer le dossier à un endroit plus approprié


import React, { useState, useEffect } from 'react';
import PatientDetail from '../../../entities/patient/patient-detail';
import Patient from '../../../entities/patient/patient';
import PatientsList from './patientslist';
import SelectionPatient from './selectionpatient';





export const VisualisationPage = () => {



    return (
        <div>
            <h1> Visualisation des données patients</h1>
              <div className='ecom-visu-global'>
                <SelectionPatient/>
                <PatientsList/>
              </div>

        </div>

    )
}

export default VisualisationPage;