import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INotes } from 'app/shared/model/notes.model';
import { getEntities } from './notes.reducer';

export const Notes = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const notesList = useAppSelector(state => state.notes.entities);
  const loading = useAppSelector(state => state.notes.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="notes-heading" data-cy="NotesHeading">
        <Translate contentKey="ecom23App.notes.home.title">Notes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.notes.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/notes/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.notes.home.createLabel">Create new Notes</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {notesList && notesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.notes.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.notes.commentaire">Commentaire</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.notes.patient">Patient</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.notes.medecin">Medecin</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {notesList.map((notes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/notes/${notes.id}`} color="link" size="sm">
                      {notes.id}
                    </Button>
                  </td>
                  <td>{notes.commentaire}</td>
                  <td>{notes.patient ? <Link to={`/medecin/${notes.patient.id}`}>{notes.patient.id}</Link> : ''}</td>
                  <td>{notes.medecin ? <Link to={`/patient/${notes.medecin.id}`}>{notes.medecin.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/notes/${notes.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/notes/${notes.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/notes/${notes.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="ecom23App.notes.home.notFound">No Notes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Notes;
