import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Administrateur from './administrateur';
import AdministrateurDetail from './administrateur-detail';
import AdministrateurUpdate from './administrateur-update';
import AdministrateurDeleteDialog from './administrateur-delete-dialog';

const AdministrateurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Administrateur />} />
    <Route path="new" element={<AdministrateurUpdate />} />
    <Route path=":id">
      <Route index element={<AdministrateurDetail />} />
      <Route path="edit" element={<AdministrateurUpdate />} />
      <Route path="delete" element={<AdministrateurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdministrateurRoutes;
