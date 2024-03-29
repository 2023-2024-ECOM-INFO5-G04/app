import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAdministrateur } from 'app/shared/model/administrateur.model';
import { getEntities } from './administrateur.reducer';

export const Administrateur = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const administrateurList = useAppSelector(state => state.administrateur.entities);
  const loading = useAppSelector(state => state.administrateur.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="administrateur-heading" data-cy="AdministrateurHeading">
        <Translate contentKey="ecom23App.administrateur.home.title">Administrateurs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.administrateur.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/administrateur/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.administrateur.home.createLabel">Create new Administrateur</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {administrateurList && administrateurList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.administrateur.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.administrateur.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.administrateur.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.administrateur.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {administrateurList.map((administrateur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/administrateur/${administrateur.id}`} color="link" size="sm">
                      {administrateur.id}
                    </Button>
                  </td>
                  <td>{administrateur.nom}</td>
                  <td>{administrateur.prenom}</td>
                  <td>{administrateur.user ? administrateur.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/administrateur/${administrateur.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/administrateur/${administrateur.id}/edit`}
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
                        to={`/administrateur/${administrateur.id}/delete`}
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
              <Translate contentKey="ecom23App.administrateur.home.notFound">No Administrateurs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Administrateur;
