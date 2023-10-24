import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './servicesoignant.reducer';

export const ServicesoignantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const servicesoignantEntity = useAppSelector(state => state.servicesoignant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="servicesoignantDetailsHeading">
          <Translate contentKey="ecom23App.servicesoignant.detail.title">Servicesoignant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{servicesoignantEntity.id}</dd>
          <dt>
            <span id="idSer">
              <Translate contentKey="ecom23App.servicesoignant.idSer">Id Ser</Translate>
            </span>
          </dt>
          <dd>{servicesoignantEntity.idSer}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="ecom23App.servicesoignant.type">Type</Translate>
            </span>
          </dt>
          <dd>{servicesoignantEntity.type}</dd>
          <dt>
            <span id="nbsoignants">
              <Translate contentKey="ecom23App.servicesoignant.nbsoignants">Nbsoignants</Translate>
            </span>
          </dt>
          <dd>{servicesoignantEntity.nbsoignants}</dd>
          <dt>
            <Translate contentKey="ecom23App.servicesoignant.infrastructure">Infrastructure</Translate>
          </dt>
          <dd>{servicesoignantEntity.infrastructure ? servicesoignantEntity.infrastructure.idE : ''}</dd>
        </dl>
        <Button tag={Link} to="/servicesoignant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servicesoignant/${servicesoignantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServicesoignantDetail;
