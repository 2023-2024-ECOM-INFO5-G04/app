import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities } from './patient.reducer';

export const Patient = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const patientList = useAppSelector(state => state.patient.entities);
  const loading = useAppSelector(state => state.patient.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="patient-heading" data-cy="PatientHeading">
        <Translate contentKey="ecom23App.patient.home.title">Patients</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ecom23App.patient.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/patient/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ecom23App.patient.home.createLabel">Create new Patient</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {patientList && patientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ecom23App.patient.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.idP">Id P</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.nomP">Nom P</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.prenomP">Prenom P</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.age">Age</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.datearrivee">Datearrivee</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.poidsactuel">Poidsactuel</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.albumine">Albumine</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.taille">Taille</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.alerte">Alerte</Translate>
                </th>
                <th>
                  <Translate contentKey="ecom23App.patient.infrastructure">Infrastructure</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {patientList.map((patient, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/patient/${patient.id}`} color="link" size="sm">
                      {patient.id}
                    </Button>
                  </td>
                  <td>{patient.idP}</td>
                  <td>{patient.nomP}</td>
                  <td>{patient.prenomP}</td>
                  <td>{patient.age}</td>
                  <td>
                    {patient.datearrivee ? <TextFormat type="date" value={patient.datearrivee} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{patient.poidsactuel}</td>
                  <td>{patient.albumine}</td>
                  <td>{patient.taille}</td>
                  <td>{patient.alerte ? <Link to={`/alerte/${patient.alerte.id}`}>{patient.alerte.id}</Link> : ''}</td>
                  <td>
                    {patient.infrastructure ? (
                      <Link to={`/etablissement/${patient.infrastructure.id}`}>{patient.infrastructure.idE}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/patient/${patient.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/patient/${patient.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/patient/${patient.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="ecom23App.patient.home.notFound">No Patients found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Patient;
