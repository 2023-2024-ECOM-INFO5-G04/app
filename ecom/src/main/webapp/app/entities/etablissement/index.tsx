import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Etablissement from './etablissement';
import EtablissementDetail from './etablissement-detail';
import EtablissementUpdate from './etablissement-update';
import EtablissementDeleteDialog from './etablissement-delete-dialog';

const EtablissementRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Etablissement />} />
    <Route path="new" element={<EtablissementUpdate />} />
    <Route path=":id">
      <Route index element={<EtablissementDetail />} />
      <Route path="edit" element={<EtablissementUpdate />} />
      <Route path="delete" element={<EtablissementDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EtablissementRoutes;
