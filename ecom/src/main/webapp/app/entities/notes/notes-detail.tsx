import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notes.reducer';

export const NotesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notesEntity = useAppSelector(state => state.notes.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notesDetailsHeading">
          <Translate contentKey="ecom23App.notes.detail.title">Notes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{notesEntity.id}</dd>
          <dt>
            <span id="commentaire">
              <Translate contentKey="ecom23App.notes.commentaire">Commentaire</Translate>
            </span>
          </dt>
          <dd>{notesEntity.commentaire}</dd>
          <dt>
            <Translate contentKey="ecom23App.notes.patient">Patient</Translate>
          </dt>
          <dd>{notesEntity.patient ? notesEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="ecom23App.notes.medecin">Medecin</Translate>
          </dt>
          <dd>{notesEntity.medecin ? notesEntity.medecin.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notes/${notesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotesDetail;
