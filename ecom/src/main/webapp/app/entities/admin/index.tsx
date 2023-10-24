import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Admin from './admin';
import AdminDetail from './admin-detail';
import AdminUpdate from './admin-update';
import AdminDeleteDialog from './admin-delete-dialog';

const AdminRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Admin />} />
    <Route path="new" element={<AdminUpdate />} />
    <Route path=":id">
      <Route index element={<AdminDetail />} />
      <Route path="edit" element={<AdminUpdate />} />
      <Route path="delete" element={<AdminDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdminRoutes;
