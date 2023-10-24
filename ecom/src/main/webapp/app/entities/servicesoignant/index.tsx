import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Servicesoignant from './servicesoignant';
import ServicesoignantDetail from './servicesoignant-detail';
import ServicesoignantUpdate from './servicesoignant-update';
import ServicesoignantDeleteDialog from './servicesoignant-delete-dialog';

const ServicesoignantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Servicesoignant />} />
    <Route path="new" element={<ServicesoignantUpdate />} />
    <Route path=":id">
      <Route index element={<ServicesoignantDetail />} />
      <Route path="edit" element={<ServicesoignantUpdate />} />
      <Route path="delete" element={<ServicesoignantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServicesoignantRoutes;
