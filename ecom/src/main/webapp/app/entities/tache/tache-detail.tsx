import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tache.reducer';

export const TacheDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tacheEntity = useAppSelector(state => state.tache.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tacheDetailsHeading">
          <Translate contentKey="ecom23App.tache.detail.title">Tache</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tacheEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="ecom23App.tache.date">Date</Translate>
            </span>
          </dt>
          <dd>{tacheEntity.date ? <TextFormat value={tacheEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="commentaire">
              <Translate contentKey="ecom23App.tache.commentaire">Commentaire</Translate>
            </span>
          </dt>
          <dd>{tacheEntity.commentaire}</dd>
          <dt>
            <span id="effectuee">
              <Translate contentKey="ecom23App.tache.effectuee">Effectuee</Translate>
            </span>
          </dt>
          <dd>{tacheEntity.effectuee ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="ecom23App.tache.patient">Patient</Translate>
          </dt>
          <dd>{tacheEntity.patient ? tacheEntity.patient.idP : ''}</dd>
          <dt>
            <Translate contentKey="ecom23App.tache.servicesoignant">Servicesoignant</Translate>
          </dt>
          <dd>{tacheEntity.servicesoignant ? tacheEntity.servicesoignant.id : ''}</dd>
          <dt>
            <Translate contentKey="ecom23App.tache.soigant">Soigant</Translate>
          </dt>
          <dd>{tacheEntity.soigant ? tacheEntity.soigant.id : ''}</dd>
          <dt>
            <Translate contentKey="ecom23App.tache.medecin">Medecin</Translate>
          </dt>
          <dd>{tacheEntity.medecin ? tacheEntity.medecin.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tache" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tache/${tacheEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TacheDetail;
