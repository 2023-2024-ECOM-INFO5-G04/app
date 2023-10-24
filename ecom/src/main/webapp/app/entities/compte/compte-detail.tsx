import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './compte.reducer';

export const CompteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const compteEntity = useAppSelector(state => state.compte.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="compteDetailsHeading">
          <Translate contentKey="ecom23App.compte.detail.title">Compte</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{compteEntity.id}</dd>
          <dt>
            <span id="nomutilisateur">
              <Translate contentKey="ecom23App.compte.nomutilisateur">Nomutilisateur</Translate>
            </span>
          </dt>
          <dd>{compteEntity.nomutilisateur}</dd>
          <dt>
            <span id="motdepasse">
              <Translate contentKey="ecom23App.compte.motdepasse">Motdepasse</Translate>
            </span>
          </dt>
          <dd>{compteEntity.motdepasse}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="ecom23App.compte.role">Role</Translate>
            </span>
          </dt>
          <dd>{compteEntity.role}</dd>
        </dl>
        <Button tag={Link} to="/compte" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/compte/${compteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompteDetail;
