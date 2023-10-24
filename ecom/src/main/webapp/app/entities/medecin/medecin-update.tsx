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
import { IPatient } from 'app/shared/model/patient.model';
import { getEntities as getPatients } from 'app/entities/patient/patient.reducer';
import { IEtablissement } from 'app/shared/model/etablissement.model';
import { getEntities as getEtablissements } from 'app/entities/etablissement/etablissement.reducer';
import { IMedecin } from 'app/shared/model/medecin.model';
import { getEntity, updateEntity, createEntity, reset } from './medecin.reducer';

export const MedecinUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const comptes = useAppSelector(state => state.compte.entities);
  const patients = useAppSelector(state => state.patient.entities);
  const etablissements = useAppSelector(state => state.etablissement.entities);
  const medecinEntity = useAppSelector(state => state.medecin.entity);
  const loading = useAppSelector(state => state.medecin.loading);
  const updating = useAppSelector(state => state.medecin.updating);
  const updateSuccess = useAppSelector(state => state.medecin.updateSuccess);

  const handleClose = () => {
    navigate('/medecin');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getComptes({}));
    dispatch(getPatients({}));
    dispatch(getEtablissements({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...medecinEntity,
      ...values,
      patients: mapIdList(values.patients),
      etablissements: mapIdList(values.etablissements),
      compte: comptes.find(it => it.id.toString() === values.compte.toString()),
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
          ...medecinEntity,
          compte: medecinEntity?.compte?.id,
          patients: medecinEntity?.patients?.map(e => e.id.toString()),
          etablissements: medecinEntity?.etablissements?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.medecin.home.createOrEditLabel" data-cy="MedecinCreateUpdateHeading">
            <Translate contentKey="ecom23App.medecin.home.createOrEditLabel">Create or edit a Medecin</Translate>
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
                  id="medecin-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ecom23App.medecin.idM')}
                id="medecin-idM"
                name="idM"
                data-cy="idM"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label={translate('ecom23App.medecin.nomM')} id="medecin-nomM" name="nomM" data-cy="nomM" type="text" />
              <ValidatedField
                label={translate('ecom23App.medecin.prenomM')}
                id="medecin-prenomM"
                name="prenomM"
                data-cy="prenomM"
                type="text"
              />
              <ValidatedField
                id="medecin-compte"
                name="compte"
                data-cy="compte"
                label={translate('ecom23App.medecin.compte')}
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
                label={translate('ecom23App.medecin.patients')}
                id="medecin-patients"
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
              <ValidatedField
                label={translate('ecom23App.medecin.etablissement')}
                id="medecin-etablissement"
                data-cy="etablissement"
                type="select"
                multiple
                name="etablissements"
              >
                <option value="" key="0" />
                {etablissements
                  ? etablissements.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/medecin" replace color="info">
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

export default MedecinUpdate;
