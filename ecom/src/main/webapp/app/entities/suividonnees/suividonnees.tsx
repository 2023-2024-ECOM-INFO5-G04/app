import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISuividonnees } from 'app/shared/model/suividonnees.model';
import { getEntities } from './suividonnees.reducer';

export const Suividonnees = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const suividonneesList = useAppSelector(state => state.suividonnees.entities);
  const loading = useAppSelector(state => state.suividonnees.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="suividonnees-heading" data-cy="SuividonneesHeading">
        <Translate contentKey="ecom23App.suividonnees.home.title">Suividonnees</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.suividonnees.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/suividonnees/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.suividonnees.home.createLabel">Create new Suividonnees</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {suividonneesList && suividonneesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.poids">Poids</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.epa">Epa</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.massecorporelle">Massecorporelle</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.quantitepoidsaliments">Quantitepoidsaliments</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.quantitecaloriesaliments">Quantitecaloriesaliments</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.absorptionreduite">Absorptionreduite</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.agression">Agression</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.suividonnees.patient">Patient</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {suividonneesList.map((suividonnees, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/suividonnees/${suividonnees.id}`} color="link" size="sm">
                      {suividonnees.id}
                    </Button>
                  </td>
                  <td>{suividonnees.date ? <TextFormat type="date" value={suividonnees.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{suividonnees.poids}</td>
                  <td>{suividonnees.epa}</td>
                  <td>{suividonnees.massecorporelle}</td>
                  <td>{suividonnees.quantitepoidsaliments}</td>
                  <td>{suividonnees.quantitecaloriesaliments}</td>
                  <td>{suividonnees.absorptionreduite ? 'true' : 'false'}</td>
                  <td>{suividonnees.agression ? 'true' : 'false'}</td>
                  <td>{suividonnees.patient ? <Link to={`/patient/${suividonnees.patient.id}`}>{suividonnees.patient.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/suividonnees/${suividonnees.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/suividonnees/${suividonnees.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/suividonnees/${suividonnees.id}/delete`}
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
              <Translate contentKey="ecom23App.suividonnees.home.notFound">No Suividonnees found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Suividonnees;
