import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Patient from './patient';
import PatientDetail from './patient-detail';
import PatientUpdate from './patient-update';
import PatientDeleteDialog from './patient-delete-dialog';

const PatientRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Patient />} />
    <Route path="new" element={<PatientUpdate />} />
    <Route path=":id">
      <Route index element={<PatientDetail />} />
      <Route path="edit" element={<PatientUpdate />} />
      <Route path="delete" element={<PatientDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PatientRoutes;
