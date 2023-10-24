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
import { IEtablissement } from 'app/shared/model/etablissement.model';
import { getEntity, updateEntity, createEntity, reset } from './etablissement.reducer';

export const EtablissementUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const medecins = useAppSelector(state => state.medecin.entities);
  const etablissementEntity = useAppSelector(state => state.etablissement.entity);
  const loading = useAppSelector(state => state.etablissement.loading);
  const updating = useAppSelector(state => state.etablissement.updating);
  const updateSuccess = useAppSelector(state => state.etablissement.updateSuccess);

  const handleClose = () => {
    navigate('/etablissement');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedecins({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...etablissementEntity,
      ...values,
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
          ...etablissementEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.etablissement.home.createOrEditLabel" data-cy="EtablissementCreateUpdateHeading">
            <Translate contentKey="ecom23App.etablissement.home.createOrEditLabel">Create or edit a Etablissement</Translate>
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
                  id="etablissement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ecom23App.etablissement.idE')}
                id="etablissement-idE"
                name="idE"
                data-cy="idE"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('ecom23App.etablissement.nomE')}
                id="etablissement-nomE"
                name="nomE"
                data-cy="nomE"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.etablissement.adresse')}
                id="etablissement-adresse"
                name="adresse"
                data-cy="adresse"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.etablissement.telephone')}
                id="etablissement-telephone"
                name="telephone"
                data-cy="telephone"
                type="text"
                validate={{
                  minLength: { value: 10, message: translate('entity.validation.minlength', { min: 10 }) },
                  maxLength: { value: 14, message: translate('entity.validation.maxlength', { max: 14 }) },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/etablissement" replace color="info">
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

export default EtablissementUpdate;
