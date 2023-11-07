import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './suividonnees.reducer';

export const SuividonneesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const suividonneesEntity = useAppSelector(state => state.suividonnees.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="suividonneesDetailsHeading">
          <Translate contentKey="ecom23App.suividonnees.detail.title">Suividonnees</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ecom23App.suividonnees.id">Id</Translate>
            </span>
          </dt>
          <dd>{suividonneesEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="ecom23App.suividonnees.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {suividonneesEntity.date ? <TextFormat value={suividonneesEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="poids">
              <Translate contentKey="ecom23App.suividonnees.poids">Poids</Translate>
            </span>
          </dt>
          <dd>{suividonneesEntity.poids}</dd>
          <dt>
            <span id="massecorporelle">
              <Translate contentKey="ecom23App.suividonnees.massecorporelle">Massecorporelle</Translate>
            </span>
          </dt>
          <dd>{suividonneesEntity.massecorporelle}</dd>
          <dt>
            <span id="quantitepoidsaliments">
              <Translate contentKey="ecom23App.suividonnees.quantitepoidsaliments">Quantitepoidsaliments</Translate>
            </span>
          </dt>
          <dd>{suividonneesEntity.quantitepoidsaliments}</dd>
          <dt>
            <span id="quantitecaloriesaliments">
              <Translate contentKey="ecom23App.suividonnees.quantitecaloriesaliments">Quantitecaloriesaliments</Translate>
            </span>
          </dt>
          <dd>{suividonneesEntity.quantitecaloriesaliments}</dd>
          <dt>
            <Translate contentKey="ecom23App.suividonnees.patient">Patient</Translate>
          </dt>
          <dd>{suividonneesEntity.patient ? suividonneesEntity.patient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/suividonnees" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/suividonnees/${suividonneesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SuividonneesDetail;
