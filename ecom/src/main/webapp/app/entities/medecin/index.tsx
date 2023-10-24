import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Medecin from './medecin';
import MedecinDetail from './medecin-detail';
import MedecinUpdate from './medecin-update';
import MedecinDeleteDialog from './medecin-delete-dialog';

const MedecinRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Medecin />} />
    <Route path="new" element={<MedecinUpdate />} />
    <Route path=":id">
      <Route index element={<MedecinDetail />} />
      <Route path="edit" element={<MedecinUpdate />} />
      <Route path="delete" element={<MedecinDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MedecinRoutes;
