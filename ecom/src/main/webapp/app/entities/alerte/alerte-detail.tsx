import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './alerte.reducer';

export const AlerteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alerteEntity = useAppSelector(state => state.alerte.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alerteDetailsHeading">
          <Translate contentKey="ecom23App.alerte.detail.title">Alerte</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{alerteEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="ecom23App.alerte.date">Date</Translate>
            </span>
          </dt>
          <dd>{alerteEntity.date ? <TextFormat value={alerteEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="commentaire">
              <Translate contentKey="ecom23App.alerte.commentaire">Commentaire</Translate>
            </span>
          </dt>
          <dd>{alerteEntity.commentaire}</dd>
          <dt>
            <span id="denutrition">
              <Translate contentKey="ecom23App.alerte.denutrition">Denutrition</Translate>
            </span>
          </dt>
          <dd>{alerteEntity.denutrition ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/alerte" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alerte/${alerteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlerteDetail;
