import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rappel from './rappel';
import RappelDetail from './rappel-detail';
import RappelUpdate from './rappel-update';
import RappelDeleteDialog from './rappel-delete-dialog';

const RappelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rappel />} />
    <Route path="new" element={<RappelUpdate />} />
    <Route path=":id">
      <Route index element={<RappelDetail />} />
      <Route path="edit" element={<RappelUpdate />} />
      <Route path="delete" element={<RappelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RappelRoutes;
