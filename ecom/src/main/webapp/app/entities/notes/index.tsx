import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Notes from './notes';
import NotesDetail from './notes-detail';
import NotesUpdate from './notes-update';
import NotesDeleteDialog from './notes-delete-dialog';

const NotesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Notes />} />
    <Route path="new" element={<NotesUpdate />} />
    <Route path=":id">
      <Route index element={<NotesDetail />} />
      <Route path="edit" element={<NotesUpdate />} />
      <Route path="delete" element={<NotesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NotesRoutes;
