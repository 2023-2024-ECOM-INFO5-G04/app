import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEtablissement } from 'app/shared/model/etablissement.model';
import { getEntities as getEtablissements } from 'app/entities/etablissement/etablissement.reducer';
import { IServicesoignant } from 'app/shared/model/servicesoignant.model';
import { getEntity, updateEntity, createEntity, reset } from './servicesoignant.reducer';

export const ServicesoignantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const etablissements = useAppSelector(state => state.etablissement.entities);
  const servicesoignantEntity = useAppSelector(state => state.servicesoignant.entity);
  const loading = useAppSelector(state => state.servicesoignant.loading);
  const updating = useAppSelector(state => state.servicesoignant.updating);
  const updateSuccess = useAppSelector(state => state.servicesoignant.updateSuccess);

  const handleClose = () => {
    navigate('/servicesoignant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEtablissements({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...servicesoignantEntity,
      ...values,
      infrastructure: etablissements.find(it => it.id.toString() === values.infrastructure.toString()),
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
          ...servicesoignantEntity,
          infrastructure: servicesoignantEntity?.infrastructure?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.servicesoignant.home.createOrEditLabel" data-cy="ServicesoignantCreateUpdateHeading">
            <Translate contentKey="ecom23App.servicesoignant.home.createOrEditLabel">Create or edit a Servicesoignant</Translate>
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
                  id="servicesoignant-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ecom23App.servicesoignant.idSer')}
                id="servicesoignant-idSer"
                name="idSer"
                data-cy="idSer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('ecom23App.servicesoignant.type')}
                id="servicesoignant-type"
                name="type"
                data-cy="type"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.servicesoignant.nbsoignants')}
                id="servicesoignant-nbsoignants"
                name="nbsoignants"
                data-cy="nbsoignants"
                type="text"
              />
              <ValidatedField
                id="servicesoignant-infrastructure"
                name="infrastructure"
                data-cy="infrastructure"
                label={translate('ecom23App.servicesoignant.infrastructure')}
                type="select"
                required
              >
                <option value="" key="0" />
                {etablissements
                  ? etablissements.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.idE}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/servicesoignant" replace color="info">
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

export default ServicesoignantUpdate;
