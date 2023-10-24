import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompte } from 'app/shared/model/compte.model';
import { getEntities as getComptes } from 'app/entities/compte/compte.reducer';
import { IServicesoignant } from 'app/shared/model/servicesoignant.model';
import { getEntities as getServicesoignants } from 'app/entities/servicesoignant/servicesoignant.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { ISoignant } from 'app/shared/model/soignant.model';
import { SoignantMetier } from 'app/shared/model/enumerations/soignant-metier.model';
import { getEntity, updateEntity, createEntity, reset } from './soignant.reducer';

export const SoignantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const comptes = useAppSelector(state => state.compte.entities);
  const servicesoignants = useAppSelector(state => state.servicesoignant.entities);
  const patients = useAppSelector(state => state.patient.entities);
  const soignantEntity = useAppSelector(state => state.soignant.entity);
  const loading = useAppSelector(state => state.soignant.loading);
  const updating = useAppSelector(state => state.soignant.updating);
  const updateSuccess = useAppSelector(state => state.soignant.updateSuccess);
  const soignantMetierValues = Object.keys(SoignantMetier);

  const handleClose = () => {
    navigate('/soignant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getComptes({}));
    dispatch(getServicesoignants({}));
    dispatch(getPatients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...soignantEntity,
      ...values,
      patients: mapIdList(values.patients),
      compte: comptes.find(it => it.id.toString() === values.compte.toString()),
      servicesoignant: servicesoignants.find(it => it.id.toString() === values.servicesoignant.toString()),
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
          metier: 'Aidesoignant',
          ...soignantEntity,
          compte: soignantEntity?.compte?.id,
          servicesoignant: soignantEntity?.servicesoignant?.id,
          patients: soignantEntity?.patients?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.soignant.home.createOrEditLabel" data-cy="SoignantCreateUpdateHeading">
            <Translate contentKey="ecom23App.soignant.home.createOrEditLabel">Create or edit a Soignant</Translate>
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
                  id="soignant-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ecom23App.soignant.idS')}
                id="soignant-idS"
                name="idS"
                data-cy="idS"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label={translate('ecom23App.soignant.nomS')} id="soignant-nomS" name="nomS" data-cy="nomS" type="text" />
              <ValidatedField
                label={translate('ecom23App.soignant.prenomS')}
                id="soignant-prenomS"
                name="prenomS"
                data-cy="prenomS"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.soignant.metier')}
                id="soignant-metier"
                name="metier"
                data-cy="metier"
                type="select"
              >
                {soignantMetierValues.map(soignantMetier => (
                  <option value={soignantMetier} key={soignantMetier}>
                    {translate('ecom23App.SoignantMetier.' + soignantMetier)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="soignant-compte"
                name="compte"
                data-cy="compte"
                label={translate('ecom23App.soignant.compte')}
                type="select"
              >
                <option value="" key="0" />
                {comptes
                  ? comptes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="soignant-servicesoignant"
                name="servicesoignant"
                data-cy="servicesoignant"
                label={translate('ecom23App.soignant.servicesoignant')}
                type="select"
                required
              >
                <option value="" key="0" />
                {servicesoignants
                  ? servicesoignants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.idSer}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('ecom23App.soignant.patients')}
                id="soignant-patients"
                data-cy="patients"
                type="select"
                multiple
                name="patients"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/soignant" replace color="info">
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

export default SoignantUpdate;
