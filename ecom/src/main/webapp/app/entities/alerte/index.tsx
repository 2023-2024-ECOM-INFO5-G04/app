import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Alerte from './alerte';
import AlerteDetail from './alerte-detail';
import AlerteUpdate from './alerte-update';
import AlerteDeleteDialog from './alerte-delete-dialog';

const AlerteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Alerte />} />
    <Route path="new" element={<AlerteUpdate />} />
    <Route path=":id">
      <Route index element={<AlerteDetail />} />
      <Route path="edit" element={<AlerteUpdate />} />
      <Route path="delete" element={<AlerteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AlerteRoutes;
