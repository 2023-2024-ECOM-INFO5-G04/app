import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISoignant } from 'app/shared/model/soignant.model';
import { getEntities } from './soignant.reducer';

export const Soignant = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const soignantList = useAppSelector(state => state.soignant.entities);
  const loading = useAppSelector(state => state.soignant.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="soignant-heading" data-cy="SoignantHeading">
        <Translate contentKey="ecom23App.soignant.home.title">Soignants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.soignant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/soignant/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.soignant.home.createLabel">Create new Soignant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {soignantList && soignantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.soignant.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.soignant.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.soignant.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.soignant.metier">Metier</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.soignant.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.soignant.servicesoignant">Servicesoignant</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.soignant.patients">Patients</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {soignantList.map((soignant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/soignant/${soignant.id}`} color="link" size="sm">
                      {soignant.id}
                    </Button>
                  </td>
                  <td>{soignant.nom}</td>
                  <td>{soignant.prenom}</td>
                  <td>
                    <Translate contentKey={`ecom23App.SoignantMetier.${soignant.metier}`} />
                  </td>
                  <td>{soignant.user ? soignant.user.id : ''}</td>
                  <td>
                    {soignant.servicesoignant ? (
                      <Link to={`/servicesoignant/${soignant.servicesoignant.id}`}>{soignant.servicesoignant.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {soignant.patients
                      ? soignant.patients.map((val, j) => (
                          <span key={j}>
                            <Link to={`/patient/${val.id}`}>{val.id}</Link>
                            {j === soignant.patients.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/soignant/${soignant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/soignant/${soignant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/soignant/${soignant.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="ecom23App.soignant.home.notFound">No Soignants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Soignant;
