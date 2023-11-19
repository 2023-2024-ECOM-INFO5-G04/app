import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISoignant } from 'app/shared/model/soignant.model';
import { getEntities as getSoignants } from 'app/entities/soignant/soignant.reducer';
import { IMedecin } from 'app/shared/model/medecin.model';
import { getEntities as getMedecins } from 'app/entities/medecin/medecin.reducer';
import { IAdministrateur } from 'app/shared/model/administrateur.model';
import { getEntities as getAdministrateurs } from 'app/entities/administrateur/administrateur.reducer';
import { ICompte } from 'app/shared/model/compte.model';
import { Role } from 'app/shared/model/enumerations/role.model';
import { getEntity, updateEntity, createEntity, reset } from './compte.reducer';

export const CompteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const soignants = useAppSelector(state => state.soignant.entities);
  const medecins = useAppSelector(state => state.medecin.entities);
  const administrateurs = useAppSelector(state => state.administrateur.entities);
  const compteEntity = useAppSelector(state => state.compte.entity);
  const loading = useAppSelector(state => state.compte.loading);
  const updating = useAppSelector(state => state.compte.updating);
  const updateSuccess = useAppSelector(state => state.compte.updateSuccess);
  const roleValues = Object.keys(Role);

  const handleClose = () => {
    navigate('/compte');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSoignants({}));
    dispatch(getMedecins({}));
    dispatch(getAdministrateurs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...compteEntity,
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
          role: 'Medecin',
          ...compteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecom23App.compte.home.createOrEditLabel" data-cy="CompteCreateUpdateHeading">
            <Translate contentKey="ecom23App.compte.home.createOrEditLabel">Create or edit a Compte</Translate>
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
                  id="compte-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('ecom23App.compte.nomutilisateur')}
                id="compte-nomutilisateur"
                name="nomutilisateur"
                data-cy="nomutilisateur"
                type="text"
              />
              <ValidatedField
                label={translate('ecom23App.compte.motdepasse')}
                id="compte-motdepasse"
                name="motdepasse"
                data-cy="motdepasse"
                type="text"
              />
              <ValidatedField label={translate('ecom23App.compte.mail')} id="compte-mail" name="mail" data-cy="mail" type="text" />
              <ValidatedField label={translate('ecom23App.compte.role')} id="compte-role" name="role" data-cy="role" type="select">
                {roleValues.map(role => (
                  <option value={role} key={role}>
                    {translate('ecom23App.Role.' + role)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/compte" replace color="info">
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

export default CompteUpdate;
