import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEtablissement } from 'app/shared/model/etablissement.model';
import { getEntities } from './etablissement.reducer';

export const Etablissement = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const etablissementList = useAppSelector(state => state.etablissement.entities);
  const loading = useAppSelector(state => state.etablissement.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="etablissement-heading" data-cy="EtablissementHeading">
        <Translate contentKey="ecom23App.etablissement.home.title">Etablissements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.etablissement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/etablissement/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.etablissement.home.createLabel">Create new Etablissement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {etablissementList && etablissementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.etablissement.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.etablissement.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.etablissement.adresse">Adresse</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.etablissement.telephone">Telephone</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {etablissementList.map((etablissement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/etablissement/${etablissement.id}`} color="link" size="sm">
                      {etablissement.id}
                    </Button>
                  </td>
                  <td>{etablissement.nom}</td>
                  <td>{etablissement.adresse}</td>
                  <td>{etablissement.telephone}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/etablissement/${etablissement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/etablissement/${etablissement.id}/edit`}
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
                        to={`/etablissement/${etablissement.id}/delete`}
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
              <Translate contentKey="ecom23App.etablissement.home.notFound">No Etablissements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Etablissement;
