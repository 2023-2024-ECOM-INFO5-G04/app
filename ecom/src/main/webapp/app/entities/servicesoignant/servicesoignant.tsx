import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IServicesoignant } from 'app/shared/model/servicesoignant.model';
import { getEntities } from './servicesoignant.reducer';

export const Servicesoignant = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const servicesoignantList = useAppSelector(state => state.servicesoignant.entities);
  const loading = useAppSelector(state => state.servicesoignant.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="servicesoignant-heading" data-cy="ServicesoignantHeading">
        <Translate contentKey="ecom23App.servicesoignant.home.title">Servicesoignants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.servicesoignant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/servicesoignant/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.servicesoignant.home.createLabel">Create new Servicesoignant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {servicesoignantList && servicesoignantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.servicesoignant.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.servicesoignant.idSer">Id Ser</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.servicesoignant.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.servicesoignant.nbsoignants">Nbsoignants</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.servicesoignant.infrastructure">Infrastructure</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {servicesoignantList.map((servicesoignant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/servicesoignant/${servicesoignant.id}`} color="link" size="sm">
                      {servicesoignant.id}
                    </Button>
                  </td>
                  <td>{servicesoignant.idSer}</td>
                  <td>{servicesoignant.type}</td>
                  <td>{servicesoignant.nbsoignants}</td>
                  <td>
                    {servicesoignant.infrastructure ? (
                      <Link to={`/etablissement/${servicesoignant.infrastructure.id}`}>{servicesoignant.infrastructure.idE}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/servicesoignant/${servicesoignant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/servicesoignant/${servicesoignant.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/servicesoignant/${servicesoignant.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="ecom23App.servicesoignant.home.notFound">No Servicesoignants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Servicesoignant;
