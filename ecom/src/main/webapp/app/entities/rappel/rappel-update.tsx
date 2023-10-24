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
import { IRappel } from 'app/shared/model/rappel.model';
import { getEntity, updateEntity, createEntity, reset } from './rappel.reducer';

export const RappelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const medecins = useAppSelector(state => state.medecin.entities);
  const rappelEntity = useAppSelector(state => state.rappel.entity);
  const loading = useAppSelector(state => state.rappel.loading);
  const updating = useAppSelector(state => state.rappel.updating);
  const updateSuccess = useAppSelector(state => state.rappel.updateSuccess);

  const handleClose = () => {
    navigate('/rappel');
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
      ...rappelEntity,
      ...values,
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
          ...rappelEntity,
          medecin: rappelEntity?.medecin?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.rappel.home.createOrEditLabel" data-cy="RappelCreateUpdateHeading">
            <Translate contentKey="ecom23App.rappel.home.createOrEditLabel">Create or edit a Rappel</Translate>
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
                  id="rappel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('ecom23App.rappel.date')} id="rappel-date" name="date" data-cy="date" type="date" />
              <ValidatedField
                label={translate('ecom23App.rappel.commentaire')}
                id="rappel-commentaire"
                name="commentaire"
                data-cy="commentaire"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.rappel.effectue')}
                id="rappel-effectue"
                name="effectue"
                data-cy="effectue"
                check
                type="checkbox"
              />
              <ValidatedField
                id="rappel-medecin"
                name="medecin"
                data-cy="medecin"
                label={translate('ecom23App.rappel.medecin')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rappel" replace color="info">
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

export default RappelUpdate;
