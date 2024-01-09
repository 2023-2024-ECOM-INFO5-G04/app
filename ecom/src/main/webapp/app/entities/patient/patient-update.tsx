import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAlerte } from 'app/shared/model/alerte.model';
import { getEntities as getAlertes } from 'app/entities/alerte/alerte.reducer';
import { IEtablissement } from 'app/shared/model/etablissement.model';
import { getEntities as getEtablissements } from 'app/entities/etablissement/etablissement.reducer';
import { IMedecin } from 'app/shared/model/medecin.model';
import { getEntities as getMedecins } from 'app/entities/medecin/medecin.reducer';
import { ISoignant } from 'app/shared/model/soignant.model';
import { getEntities as getSoignants } from 'app/entities/soignant/soignant.reducer';
import { IPatient } from 'app/shared/model/patient.model';
import { Sexe } from 'app/shared/model/enumerations/sexe.model';
import { getEntity, updateEntity, createEntity, reset } from './patient.reducer';

export const PatientUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const alertes = useAppSelector(state => state.alerte.entities);
  const etablissements = useAppSelector(state => state.etablissement.entities);
  const medecins = useAppSelector(state => state.medecin.entities);
  const soignants = useAppSelector(state => state.soignant.entities);
  const patientEntity = useAppSelector(state => state.patient.entity);
  const loading = useAppSelector(state => state.patient.loading);
  const updating = useAppSelector(state => state.patient.updating);
  const updateSuccess = useAppSelector(state => state.patient.updateSuccess);
  const sexeValues = Object.keys(Sexe);

  const handleClose = () => {
    navigate('/patient');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAlertes({}));
    dispatch(getEtablissements({}));
    dispatch(getMedecins({}));
    dispatch(getSoignants({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...patientEntity,
      ...values,
      alerte: alertes.find(it => it.id.toString() === values.alerte.toString()),
      etablissement: etablissements.find(it => it.id.toString() === values.etablissement.toString()),
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
          sexe: 'M',
          ...patientEntity,
          alerte: patientEntity?.alerte?.id,
          etablissement: patientEntity?.etablissement?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.patient.home.createOrEditLabel" data-cy="PatientCreateUpdateHeading">
            <Translate contentKey="ecom23App.patient.home.createOrEditLabel">Create or edit a Patient</Translate>
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
                  id="patient-id"
                  label={translate('ecom23App.patient.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('ecom23App.patient.nom')} id="patient-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField
                label={translate('ecom23App.patient.prenom')}
                id="patient-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <ValidatedField label={translate('ecom23App.patient.age')} id="patient-age" name="age" data-cy="age" type="text" />
              <ValidatedField
                label={translate('ecom23App.patient.datearrivee')}
                id="patient-datearrivee"
                name="datearrivee"
                data-cy="datearrivee"
                type="date"
              />
              <ValidatedField
                label={translate('ecom23App.patient.poidsactuel')}
                id="patient-poidsactuel"
                name="poidsactuel"
                data-cy="poidsactuel"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.patient.albumine')}
                id="patient-albumine"
                name="albumine"
                data-cy="albumine"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.patient.taille')}
                id="patient-taille"
                name="taille"
                data-cy="taille"
                type="text"
              />
              <ValidatedField label={translate('ecom23App.patient.sexe')} id="patient-sexe" name="sexe" data-cy="sexe" type="select">
                {sexeValues.map(sexe => (
                  <option value={sexe} key={sexe}>
                    {translate('ecom23App.Sexe.' + sexe)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('ecom23App.patient.favori')}
                id="patient-favori"
                name="favori"
                data-cy="favori"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('ecom23App.patient.sarcopenie')}
                id="patient-sarcopenie"
                name="sarcopenie"
                data-cy="sarcopenie"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('ecom23App.patient.absorptionreduite')}
                id="patient-absorptionreduite"
                name="absorptionreduite"
                data-cy="absorptionreduite"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('ecom23App.patient.agression')}
                id="patient-agression"
                name="agression"
                data-cy="agression"
                check
                type="checkbox"
              />
              <ValidatedField
                id="patient-alerte"
                name="alerte"
                data-cy="alerte"
                label={translate('ecom23App.patient.alerte')}
                type="select"
              >
                <option value="" key="0" />
                {alertes
                  ? alertes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="patient-etablissement"
                name="etablissement"
                data-cy="etablissement"
                label={translate('ecom23App.patient.etablissement')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/patient" replace color="info">
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

export default PatientUpdate;
