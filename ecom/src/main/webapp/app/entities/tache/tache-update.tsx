import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IServicesoignant } from 'app/shared/model/servicesoignant.model';
import { getEntities as getServicesoignants } from 'app/entities/servicesoignant/servicesoignant.reducer';
import { ISoignant } from 'app/shared/model/soignant.model';
import { getEntities as getSoignants } from 'app/entities/soignant/soignant.reducer';
import { IMedecin } from 'app/shared/model/medecin.model';
import { getEntities as getMedecins } from 'app/entities/medecin/medecin.reducer';
import { ITache } from 'app/shared/model/tache.model';
import { getEntity, updateEntity, createEntity, reset } from './tache.reducer';

export const TacheUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const patients = useAppSelector(state => state.patient.entities);
  const servicesoignants = useAppSelector(state => state.servicesoignant.entities);
  const soignants = useAppSelector(state => state.soignant.entities);
  const medecins = useAppSelector(state => state.medecin.entities);
  const tacheEntity = useAppSelector(state => state.tache.entity);
  const loading = useAppSelector(state => state.tache.loading);
  const updating = useAppSelector(state => state.tache.updating);
  const updateSuccess = useAppSelector(state => state.tache.updateSuccess);

  const handleClose = () => {
    navigate('/tache');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPatients({}));
    dispatch(getServicesoignants({}));
    dispatch(getSoignants({}));
    dispatch(getMedecins({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tacheEntity,
      ...values,
      patient: patients.find(it => it.id.toString() === values.patient.toString()),
      servicesoignant: servicesoignants.find(it => it.id.toString() === values.servicesoignant.toString()),
      soignant: soignants.find(it => it.id.toString() === values.soignant.toString()),
      medecin: medecins.find(it => it.id.toString() === values.medecin.toString()),
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
          ...tacheEntity,
          patient: tacheEntity?.patient?.id,
          servicesoignant: tacheEntity?.servicesoignant?.id,
          soignant: tacheEntity?.soignant?.id,
          medecin: tacheEntity?.medecin?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.tache.home.createOrEditLabel" data-cy="TacheCreateUpdateHeading">
            <Translate contentKey="ecom23App.tache.home.createOrEditLabel">Create or edit a Tache</Translate>
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
                  id="tache-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('ecom23App.tache.date')} id="tache-date" name="date" data-cy="date" type="date" />
              <ValidatedField
                label={translate('ecom23App.tache.commentaire')}
                id="tache-commentaire"
                name="commentaire"
                data-cy="commentaire"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.tache.effectuee')}
                id="tache-effectuee"
                name="effectuee"
                data-cy="effectuee"
                check
                type="checkbox"
              />
              <ValidatedField
                id="tache-patient"
                name="patient"
                data-cy="patient"
                label={translate('ecom23App.tache.patient')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="tache-servicesoignant"
                name="servicesoignant"
                data-cy="servicesoignant"
                label={translate('ecom23App.tache.servicesoignant')}
                type="select"
              >
                <option value="" key="0" />
                {servicesoignants
                  ? servicesoignants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="tache-soignant"
                name="soignant"
                data-cy="soignant"
                label={translate('ecom23App.tache.soignant')}
                type="select"
              >
                <option value="" key="0" />
                {soignants
                  ? soignants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="tache-medecin"
                name="medecin"
                data-cy="medecin"
                label={translate('ecom23App.tache.medecin')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tache" replace color="info">
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

export default TacheUpdate;
