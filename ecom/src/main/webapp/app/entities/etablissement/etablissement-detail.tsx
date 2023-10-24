import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './etablissement.reducer';

export const EtablissementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const etablissementEntity = useAppSelector(state => state.etablissement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="etablissementDetailsHeading">
          <Translate contentKey="ecom23App.etablissement.detail.title">Etablissement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.id}</dd>
          <dt>
            <span id="idE">
              <Translate contentKey="ecom23App.etablissement.idE">Id E</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.idE}</dd>
          <dt>
            <span id="nomE">
              <Translate contentKey="ecom23App.etablissement.nomE">Nom E</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.nomE}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="ecom23App.etablissement.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.adresse}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="ecom23App.etablissement.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{etablissementEntity.telephone}</dd>
        </dl>
        <Button tag={Link} to="/etablissement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/etablissement/${etablissementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EtablissementDetail;
