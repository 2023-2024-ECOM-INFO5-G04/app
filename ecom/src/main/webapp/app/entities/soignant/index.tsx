import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soignant from './soignant';
import SoignantDetail from './soignant-detail';
import SoignantUpdate from './soignant-update';
import SoignantDeleteDialog from './soignant-delete-dialog';

const SoignantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soignant />} />
    <Route path="new" element={<SoignantUpdate />} />
    <Route path=":id">
      <Route index element={<SoignantDetail />} />
      <Route path="edit" element={<SoignantUpdate />} />
      <Route path="delete" element={<SoignantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoignantRoutes;
