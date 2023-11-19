import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMedecin } from 'app/shared/model/medecin.model';
import { getEntities } from './medecin.reducer';

export const Medecin = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const medecinList = useAppSelector(state => state.medecin.entities);
  const loading = useAppSelector(state => state.medecin.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="medecin-heading" data-cy="MedecinHeading">
        <Translate contentKey="ecom23App.medecin.home.title">Medecins</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.medecin.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/medecin/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.medecin.home.createLabel">Create new Medecin</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {medecinList && medecinList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.medecin.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.medecin.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.medecin.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.medecin.compte">Compte</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.medecin.patients">Patients</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.medecin.etablissement">Etablissement</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {medecinList.map((medecin, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/medecin/${medecin.id}`} color="link" size="sm">
                      {medecin.id}
                    </Button>
                  </td>
                  <td>{medecin.nom}</td>
                  <td>{medecin.prenom}</td>
                  <td>{medecin.compte ? <Link to={`/compte/${medecin.compte.id}`}>{medecin.compte.id}</Link> : ''}</td>
                  <td>
                    {medecin.patients
                      ? medecin.patients.map((val, j) => (
                          <span key={j}>
                            <Link to={`/patient/${val.id}`}>{val.id}</Link>
                            {j === medecin.patients.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {medecin.etablissements
                      ? medecin.etablissements.map((val, j) => (
                          <span key={j}>
                            <Link to={`/etablissement/${val.id}`}>{val.id}</Link>
                            {j === medecin.etablissements.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/medecin/${medecin.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/medecin/${medecin.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/medecin/${medecin.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="ecom23App.medecin.home.notFound">No Medecins found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Medecin;
