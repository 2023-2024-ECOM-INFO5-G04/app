import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Etablissement from './etablissement';
import Servicesoignant from './servicesoignant';
import Soignant from './soignant';
import Administrateur from './administrateur';
import Tache from './tache';
import Medecin from './medecin';
import Rappel from './rappel';
import Patient from './patient';
import Alerte from './alerte';
import Suividonnees from './suividonnees';
import Notes from './notes';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="etablissement/*" element={<Etablissement />} />
        <Route path="servicesoignant/*" element={<Servicesoignant />} />
        <Route path="soignant/*" element={<Soignant />} />
        <Route path="administrateur/*" element={<Administrateur />} />
        <Route path="tache/*" element={<Tache />} />
        <Route path="medecin/*" element={<Medecin />} />
        <Route path="rappel/*" element={<Rappel />} />
        <Route path="patient/*" element={<Patient />} />
        <Route path="alerte/*" element={<Alerte />} />
        <Route path="suividonnees/*" element={<Suividonnees />} />
        <Route path="notes/*" element={<Notes />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
