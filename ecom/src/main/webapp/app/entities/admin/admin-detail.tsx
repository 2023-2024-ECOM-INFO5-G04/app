import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './admin.reducer';

export const AdminDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const adminEntity = useAppSelector(state => state.admin.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adminDetailsHeading">
          <Translate contentKey="ecom23App.admin.detail.title">Admin</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adminEntity.id}</dd>
          <dt>
            <span id="idA">
              <Translate contentKey="ecom23App.admin.idA">Id A</Translate>
            </span>
          </dt>
          <dd>{adminEntity.idA}</dd>
          <dt>
            <span id="nomA">
              <Translate contentKey="ecom23App.admin.nomA">Nom A</Translate>
            </span>
          </dt>
          <dd>{adminEntity.nomA}</dd>
          <dt>
            <span id="prenomA">
              <Translate contentKey="ecom23App.admin.prenomA">Prenom A</Translate>
            </span>
          </dt>
          <dd>{adminEntity.prenomA}</dd>
          <dt>
            <Translate contentKey="ecom23App.admin.compte">Compte</Translate>
          </dt>
          <dd>{adminEntity.compte ? adminEntity.compte.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/admin" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admin/${adminEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdminDetail;
