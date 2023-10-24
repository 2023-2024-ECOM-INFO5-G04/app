import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMedecin } from 'app/shared/model/medecin.model';
import { getEntities as getMedecins } from 'app/entities/medecin/medecin.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { INotes } from 'app/shared/model/notes.model';
import { getEntity, updateEntity, createEntity, reset } from './notes.reducer';

export const NotesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const medecins = useAppSelector(state => state.medecin.entities);
  const patients = useAppSelector(state => state.patient.entities);
  const notesEntity = useAppSelector(state => state.notes.entity);
  const loading = useAppSelector(state => state.notes.loading);
  const updating = useAppSelector(state => state.notes.updating);
  const updateSuccess = useAppSelector(state => state.notes.updateSuccess);

  const handleClose = () => {
    navigate('/notes');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedecins({}));
    dispatch(getPatients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...notesEntity,
      ...values,
      patient: medecins.find(it => it.id.toString() === values.patient.toString()),
      medecin: patients.find(it => it.id.toString() === values.medecin.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...notesEntity,
          patient: notesEntity?.patient?.id,
          medecin: notesEntity?.medecin?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.notes.home.createOrEditLabel" data-cy="NotesCreateUpdateHeading">
            <Translate contentKey="ecom23App.notes.home.createOrEditLabel">Create or edit a Notes</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="notes-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ecom23App.notes.commentaire')}
                id="notes-commentaire"
                name="commentaire"
                data-cy="commentaire"
                type="text"
              />
              <ValidatedField
                id="notes-patient"
                name="patient"
                data-cy="patient"
                label={translate('ecom23App.notes.patient')}
                type="select"
              >
                <option value="" key="0" />
                {medecins
                  ? medecins.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="notes-medecin"
                name="medecin"
                data-cy="medecin"
                label={translate('ecom23App.notes.medecin')}
                type="select"
              >
                <option value="" key="0" />
                {patients
                  ? patients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notes" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default NotesUpdate;
