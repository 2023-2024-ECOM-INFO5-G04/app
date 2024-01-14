import './home.scss';

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch, faStethoscope } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';

import { useAppSelector } from 'app/config/store';

import donneesRappel, { RappelData } from '../medecin-interface/classes/rappels-class';
import Rappel from './rappels';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const roles = account.authorities;
  const isMed = roles?.includes('ROLE_MEDECIN') ?? false;



  const [rappels, setRappels] = useState(null);
  const [requeteEffectuee, setRequeteEffectuee] = useState(false);

  let resp;

  useEffect(() => {
    if (!requeteEffectuee && isMed) {
      axios
        .get('api/rappels')
        .then(response => {
          console.log("Réponse de l'API :", response.data);
          resp = donneesRappel(response.data);
        })
        .then(patientData => setRappels(resp))

        .catch(error => {
          if (error.response) {
            // La requête a été effectuée, mais le serveur a répondu avec un code d'erreur
            console.log('Erreur de réponse du serveur :', error.response.data);
            console.log('Statut de la réponse du serveur :', error.response.status);
          } else if (error.request) {
            // La requête a été effectuée, mais aucune réponse n'a été reçue
            console.log('Aucune réponse reçue du serveur');
          } else {
            // Une erreur s'est produite lors de la configuration de la requête
            console.log('Erreur de configuration de la requête :', error.message);
          }
          console.log('Erreur complète :', error.config);
        });
    }
  }, [requeteEffectuee]);

  function trierParDate(data: RappelData[]): RappelData[] {
    const dataTriee = data
      .map(item => ({ ...item, date: new Date(item.date) }))
      .sort((a, b) => a.date.getTime() - b.date.getTime())
      .map(item => ({ ...item, date: item.date.toISOString().split('T')[0] }));

    return dataTriee;
  }

  return (
    <Row style={{ height: '80vh' }}>
      <Col md="4" className="pad" style={{ backgroundColor: '##c6c6c6' }}>


        {isMed && (
          rappels ? (
            <div>
              <h2 style={{ marginTop: '15px' }}> Pour ne rien oublier :</h2>
              <Rappel
                rappels={trierParDate(rappels)}
              /> </div>) : (<div>
                <FontAwesomeIcon icon={faStethoscope} />
              </div>)
        )}



      </Col>
      <Col md="3" className='col2'>
        <h2 className='titleH'>
          Cher client, bienvenue !
        </h2>


        {isMed && (
          <Link className='custom-link'
            to="/visualisation">
            <FontAwesomeIcon icon={faSearch} />
            Rechercher un patient

          </Link>)}

        <span className="hipster rounded" />
        <div style={{ fontSize: 'xx-small' }}>
          Image de <a href="https://fr.freepik.com/vecteurs-libre/medecin-tenant-presse-papiers_2094267.htm#query=medecine&position=11&from_view=search&track=sph&uuid=58253a42-6d76-4fd7-add1-d629f8eb26d6">Freepik</a>
        </div>
      </Col>
      <Col md="4">

        {
          account?.login ? (
            <div>
              <Alert color="success">
                <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                  You are logged in as user {account.login}.
                </Translate>
              </Alert>

            </div>
          ) : (
            <div>
              <Alert color="warning">
                Attention ! Vous n'êtes pas connecté.e.
              </Alert>
            </div>
          )}

      </Col>
    </Row>
  );
};

export default Home;
