import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompte } from 'app/shared/model/compte.model';
import { getEntities } from './compte.reducer';

export const Compte = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const compteList = useAppSelector(state => state.compte.entities);
  const loading = useAppSelector(state => state.compte.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="compte-heading" data-cy="CompteHeading">
        <Translate contentKey="ecom23App.compte.home.title">Comptes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.compte.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/compte/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.compte.home.createLabel">Create new Compte</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {compteList && compteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.compte.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.compte.nomutilisateur">Nomutilisateur</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.compte.motdepasse">Motdepasse</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.compte.role">Role</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {compteList.map((compte, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/compte/${compte.id}`} color="link" size="sm">
                      {compte.id}
                    </Button>
                  </td>
                  <td>{compte.nomutilisateur}</td>
                  <td>{compte.motdepasse}</td>
                  <td>
                    <Translate contentKey={`ecom23App.Role.${compte.role}`} />
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/compte/${compte.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/compte/${compte.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/compte/${compte.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="ecom23App.compte.home.notFound">No Comptes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Compte;
