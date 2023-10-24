import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Suividonnees from './suividonnees';
import SuividonneesDetail from './suividonnees-detail';
import SuividonneesUpdate from './suividonnees-update';
import SuividonneesDeleteDialog from './suividonnees-delete-dialog';

const SuividonneesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Suividonnees />} />
    <Route path="new" element={<SuividonneesUpdate />} />
    <Route path=":id">
      <Route index element={<SuividonneesDetail />} />
      <Route path="edit" element={<SuividonneesUpdate />} />
      <Route path="delete" element={<SuividonneesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SuividonneesRoutes;
