import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Compte from './compte';
import CompteDetail from './compte-detail';
import CompteUpdate from './compte-update';
import CompteDeleteDialog from './compte-delete-dialog';

const CompteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Compte />} />
    <Route path="new" element={<CompteUpdate />} />
    <Route path=":id">
      <Route index element={<CompteDetail />} />
      <Route path="edit" element={<CompteUpdate />} />
      <Route path="delete" element={<CompteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompteRoutes;
