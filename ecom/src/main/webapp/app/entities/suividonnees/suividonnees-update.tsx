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
import { ISuividonnees } from 'app/shared/model/suividonnees.model';
import { getEntity, updateEntity, createEntity, reset } from './suividonnees.reducer';

export const SuividonneesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const patients = useAppSelector(state => state.patient.entities);
  const suividonneesEntity = useAppSelector(state => state.suividonnees.entity);
  const loading = useAppSelector(state => state.suividonnees.loading);
  const updating = useAppSelector(state => state.suividonnees.updating);
  const updateSuccess = useAppSelector(state => state.suividonnees.updateSuccess);

  const handleClose = () => {
    navigate('/suividonnees');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPatients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...suividonneesEntity,
      ...values,
      patient: patients.find(it => it.id.toString() === values.patient.toString()),
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
          ...suividonneesEntity,
          patient: suividonneesEntity?.patient?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.suividonnees.home.createOrEditLabel" data-cy="SuividonneesCreateUpdateHeading">
            <Translate contentKey="ecom23App.suividonnees.home.createOrEditLabel">Create or edit a Suividonnees</Translate>
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
                  id="suividonnees-id"
                  label={translate('ecom23App.suividonnees.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ecom23App.suividonnees.date')}
                id="suividonnees-date"
                name="date"
                data-cy="date"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('ecom23App.suividonnees.poids')}
                id="suividonnees-poids"
                name="poids"
                data-cy="poids"
                type="text"
              />
              <ValidatedField label={translate('ecom23App.suividonnees.epa')} id="suividonnees-epa" name="epa" data-cy="epa" type="text" />
              <ValidatedField
                label={translate('ecom23App.suividonnees.massecorporelle')}
                id="suividonnees-massecorporelle"
                name="massecorporelle"
                data-cy="massecorporelle"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.suividonnees.quantitepoidsaliments')}
                id="suividonnees-quantitepoidsaliments"
                name="quantitepoidsaliments"
                data-cy="quantitepoidsaliments"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.suividonnees.quantitecaloriesaliments')}
                id="suividonnees-quantitecaloriesaliments"
                name="quantitecaloriesaliments"
                data-cy="quantitecaloriesaliments"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.suividonnees.absorptionreduite')}
                id="suividonnees-absorptionreduite"
                name="absorptionreduite"
                data-cy="absorptionreduite"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('ecom23App.suividonnees.agression')}
                id="suividonnees-agression"
                name="agression"
                data-cy="agression"
                check
                type="checkbox"
              />
              <ValidatedField
                id="suividonnees-patient"
                name="patient"
                data-cy="patient"
                label={translate('ecom23App.suividonnees.patient')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/suividonnees" replace color="info">
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

export default SuividonneesUpdate;
