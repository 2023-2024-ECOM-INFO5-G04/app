import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rappel.reducer';

export const RappelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rappelEntity = useAppSelector(state => state.rappel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rappelDetailsHeading">
          <Translate contentKey="ecom23App.rappel.detail.title">Rappel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rappelEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="ecom23App.rappel.date">Date</Translate>
            </span>
          </dt>
          <dd>{rappelEntity.date ? <TextFormat value={rappelEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="commentaire">
              <Translate contentKey="ecom23App.rappel.commentaire">Commentaire</Translate>
            </span>
          </dt>
          <dd>{rappelEntity.commentaire}</dd>
          <dt>
            <span id="effectue">
              <Translate contentKey="ecom23App.rappel.effectue">Effectue</Translate>
            </span>
          </dt>
          <dd>{rappelEntity.effectue ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="ecom23App.rappel.medecin">Medecin</Translate>
          </dt>
          <dd>{rappelEntity.medecin ? rappelEntity.medecin.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rappel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rappel/${rappelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RappelDetail;
