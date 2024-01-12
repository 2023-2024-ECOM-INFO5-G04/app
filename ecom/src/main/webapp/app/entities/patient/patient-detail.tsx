import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './patient.reducer';

export const PatientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const patientEntity = useAppSelector(state => state.patient.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientDetailsHeading">
          <Translate contentKey="ecom23App.patient.detail.title">Patient</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ecom23App.patient.id">Id</Translate>
            </span>
          </dt>
          <dd>{patientEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="ecom23App.patient.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{patientEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="ecom23App.patient.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{patientEntity.prenom}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="ecom23App.patient.age">Age</Translate>
            </span>
          </dt>
          <dd>{patientEntity.age}</dd>
          <dt>
            <span id="datearrivee">
              <Translate contentKey="ecom23App.patient.datearrivee">Datearrivee</Translate>
            </span>
          </dt>
          <dd>
            {patientEntity.datearrivee ? <TextFormat value={patientEntity.datearrivee} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="poidsactuel">
              <Translate contentKey="ecom23App.patient.poidsactuel">Poidsactuel</Translate>
            </span>
          </dt>
          <dd>{patientEntity.poidsactuel}</dd>
          <dt>
            <span id="albumine">
              <Translate contentKey="ecom23App.patient.albumine">Albumine</Translate>
            </span>
          </dt>
          <dd>{patientEntity.albumine}</dd>
          <dt>
            <span id="taille">
              <Translate contentKey="ecom23App.patient.taille">Taille</Translate>
            </span>
          </dt>
          <dd>{patientEntity.taille}</dd>
          <dt>
            <span id="sexe">
              <Translate contentKey="ecom23App.patient.sexe">Sexe</Translate>
            </span>
          </dt>
          <dd>{patientEntity.sexe}</dd>
          <dt>
            <span id="favori">
              <Translate contentKey="ecom23App.patient.favori">Favori</Translate>
            </span>
          </dt>
          <dd>{patientEntity.favori ? 'true' : 'false'}</dd>
          <dt>
            <span id="sarcopenie">
              <Translate contentKey="ecom23App.patient.sarcopenie">Sarcopenie</Translate>
            </span>
          </dt>
          <dd>{patientEntity.sarcopenie ? 'true' : 'false'}</dd>
          <dt>
            <span id="absorptionreduite">
              <Translate contentKey="ecom23App.patient.absorptionreduite">Absorptionreduite</Translate>
            </span>
          </dt>
          <dd>{patientEntity.absorptionreduite ? 'true' : 'false'}</dd>
          <dt>
            <span id="agression">
              <Translate contentKey="ecom23App.patient.agression">Agression</Translate>
            </span>
          </dt>
          <dd>{patientEntity.agression ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="ecom23App.patient.alerte">Alerte</Translate>
          </dt>
          <dd>{patientEntity.alerte ? patientEntity.alerte.id : ''}</dd>
          <dt>
            <Translate contentKey="ecom23App.patient.etablissement">Etablissement</Translate>
          </dt>
          <dd>{patientEntity.etablissement ? patientEntity.etablissement.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/patient" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patient/${patientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientDetail;
