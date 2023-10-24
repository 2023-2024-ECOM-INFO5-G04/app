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
import { IAdmin } from 'app/shared/model/admin.model';
import { getEntity, updateEntity, createEntity, reset } from './admin.reducer';

export const AdminUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const comptes = useAppSelector(state => state.compte.entities);
  const adminEntity = useAppSelector(state => state.admin.entity);
  const loading = useAppSelector(state => state.admin.loading);
  const updating = useAppSelector(state => state.admin.updating);
  const updateSuccess = useAppSelector(state => state.admin.updateSuccess);

  const handleClose = () => {
    navigate('/admin');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getComptes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...adminEntity,
      ...values,
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
          ...adminEntity,
          compte: adminEntity?.compte?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.admin.home.createOrEditLabel" data-cy="AdminCreateUpdateHeading">
            <Translate contentKey="ecom23App.admin.home.createOrEditLabel">Create or edit a Admin</Translate>
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
                  id="admin-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ecom23App.admin.idA')}
                id="admin-idA"
                name="idA"
                data-cy="idA"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label={translate('ecom23App.admin.nomA')} id="admin-nomA" name="nomA" data-cy="nomA" type="text" />
              <ValidatedField
                label={translate('ecom23App.admin.prenomA')}
                id="admin-prenomA"
                name="prenomA"
                data-cy="prenomA"
                type="text"
              />
              <ValidatedField id="admin-compte" name="compte" data-cy="compte" label={translate('ecom23App.admin.compte')} type="select">
                <option value="" key="0" />
                {comptes
                  ? comptes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/admin" replace color="info">
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

export default AdminUpdate;
