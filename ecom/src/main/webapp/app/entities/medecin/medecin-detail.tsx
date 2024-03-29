import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './medecin.reducer';

export const MedecinDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const medecinEntity = useAppSelector(state => state.medecin.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="medecinDetailsHeading">
          <Translate contentKey="ecom23App.medecin.detail.title">Medecin</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ecom23App.medecin.id">Id</Translate>
            </span>
          </dt>
          <dd>{medecinEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="ecom23App.medecin.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{medecinEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="ecom23App.medecin.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{medecinEntity.prenom}</dd>
          <dt>
            <Translate contentKey="ecom23App.medecin.user">User</Translate>
          </dt>
          <dd>{medecinEntity.user ? medecinEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="ecom23App.medecin.patients">Patients</Translate>
          </dt>
          <dd>
            {medecinEntity.patients
              ? medecinEntity.patients.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {medecinEntity.patients && i === medecinEntity.patients.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="ecom23App.medecin.etablissement">Etablissement</Translate>
          </dt>
          <dd>
            {medecinEntity.etablissements
              ? medecinEntity.etablissements.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {medecinEntity.etablissements && i === medecinEntity.etablissements.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/medecin" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medecin/${medecinEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MedecinDetail;
