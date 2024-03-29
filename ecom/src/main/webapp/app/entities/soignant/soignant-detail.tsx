import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './soignant.reducer';

export const SoignantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const soignantEntity = useAppSelector(state => state.soignant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="soignantDetailsHeading">
          <Translate contentKey="ecom23App.soignant.detail.title">Soignant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ecom23App.soignant.id">Id</Translate>
            </span>
          </dt>
          <dd>{soignantEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="ecom23App.soignant.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{soignantEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="ecom23App.soignant.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{soignantEntity.prenom}</dd>
          <dt>
            <span id="metier">
              <Translate contentKey="ecom23App.soignant.metier">Metier</Translate>
            </span>
          </dt>
          <dd>{soignantEntity.metier}</dd>
          <dt>
            <Translate contentKey="ecom23App.soignant.user">User</Translate>
          </dt>
          <dd>{soignantEntity.user ? soignantEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="ecom23App.soignant.servicesoignant">Servicesoignant</Translate>
          </dt>
          <dd>{soignantEntity.servicesoignant ? soignantEntity.servicesoignant.id : ''}</dd>
          <dt>
            <Translate contentKey="ecom23App.soignant.patients">Patients</Translate>
          </dt>
          <dd>
            {soignantEntity.patients
              ? soignantEntity.patients.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {soignantEntity.patients && i === soignantEntity.patients.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/soignant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/soignant/${soignantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SoignantDetail;
