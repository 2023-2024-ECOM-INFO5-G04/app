import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITache } from 'app/shared/model/tache.model';
import { getEntities } from './tache.reducer';

export const Tache = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const tacheList = useAppSelector(state => state.tache.entities);
  const loading = useAppSelector(state => state.tache.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="tache-heading" data-cy="TacheHeading">
        <Translate contentKey="ecom23App.tache.home.title">Taches</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.tache.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tache/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.tache.home.createLabel">Create new Tache</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tacheList && tacheList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.tache.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.tache.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.tache.commentaire">Commentaire</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.tache.effectuee">Effectuee</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.tache.patient">Patient</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.tache.servicesoignant">Servicesoignant</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.tache.soignant">Soignant</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.tache.medecin">Medecin</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tacheList.map((tache, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tache/${tache.id}`} color="link" size="sm">
                      {tache.id}
                    </Button>
                  </td>
                  <td>{tache.date ? <TextFormat type="date" value={tache.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{tache.commentaire}</td>
                  <td>{tache.effectuee ? 'true' : 'false'}</td>
                  <td>{tache.patient ? <Link to={`/patient/${tache.patient.id}`}>{tache.patient.id}</Link> : ''}</td>
                  <td>
                    {tache.servicesoignant ? (
                      <Link to={`/servicesoignant/${tache.servicesoignant.id}`}>{tache.servicesoignant.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{tache.soignant ? <Link to={`/soignant/${tache.soignant.id}`}>{tache.soignant.id}</Link> : ''}</td>
                  <td>{tache.medecin ? <Link to={`/medecin/${tache.medecin.id}`}>{tache.medecin.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tache/${tache.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tache/${tache.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tache/${tache.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="ecom23App.tache.home.notFound">No Taches found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Tache;
