import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlerte } from 'app/shared/model/alerte.model';
import { getEntities } from './alerte.reducer';

export const Alerte = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const alerteList = useAppSelector(state => state.alerte.entities);
  const loading = useAppSelector(state => state.alerte.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="alerte-heading" data-cy="AlerteHeading">
        <Translate contentKey="ecom23App.alerte.home.title">Alertes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.alerte.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/alerte/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.alerte.home.createLabel">Create new Alerte</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {alerteList && alerteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.alerte.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.alerte.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.alerte.commentaire">Commentaire</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.alerte.denutrition">Denutrition</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {alerteList.map((alerte, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/alerte/${alerte.id}`} color="link" size="sm">
                      {alerte.id}
                    </Button>
                  </td>
                  <td>{alerte.date ? <TextFormat type="date" value={alerte.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{alerte.commentaire}</td>
                  <td>{alerte.denutrition ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/alerte/${alerte.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/alerte/${alerte.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/alerte/${alerte.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="ecom23App.alerte.home.notFound">No Alertes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Alerte;
