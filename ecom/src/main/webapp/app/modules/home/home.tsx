import './home.scss';

import Swal from 'sweetalert2';

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch, faStethoscope } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';

import { useAppSelector } from 'app/config/store';

import donneesRappel, { RappelData } from '../medecin-interface/classes/rappels-class';
import Rappel from './rappels';
import FormRappel from './formRappel';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const roles = account.authorities;
  const isMed = roles?.includes('ROLE_MEDECIN') ?? false;

  const [rappels, setRappels] = useState(null);
  const [requeteEffectueeR, setRequeteEffectueeR] = useState(false);
  const [rappelCreation, setRappelCreation] = useState(false);

  const date = new Date();
  const day = date.getDate();
  const mOnth = date.getMonth() + 1;
  const month = mOnth < 10 ? '0' + mOnth : mOnth;
  const year = date.getFullYear();

  const dateFormatted = year.toString() + '-' + month + '-' + day.toString();



  let resp;

  useEffect(() => {
    if (!requeteEffectueeR && isMed) {
      axios
        .get('api/rappels')
        .then(response => {
          console.log("Réponse de l'API :", response.data);
          resp = donneesRappel(response.data);
        })
        .then(() => {
          setRappels(resp)
          setRequeteEffectueeR(true);
        })
        .catch(error => {
          if (error.response) {
            console.log('Erreur de réponse du serveur :', error.response.data);
            console.log('Statut de la réponse du serveur :', error.response.status);
          } else if (error.request) {
            console.log('Aucune réponse reçue du serveur');
          } else {
            console.log('Erreur de configuration de la requête :', error.message);
          }
          console.log('Erreur complète :', error.config);
        });
    }
  }, [requeteEffectueeR]);


  function trierParDate(data: RappelData[]): RappelData[] {
    const dataTriee = data
      .map(item => ({ ...item, date: new Date(item.date) }))
      .sort((a, b) => a.date.getTime() - b.date.getTime())
      .map(item => ({ ...item, date: item.date.toISOString().split('T')[0] }));
      const dataSansPerimes = dataTriee.filter(rappel => (new Date(rappel.date)).getTime() >= Date.now());


    return dataSansPerimes;
  }

  

  function restreindreRappels10(data: RappelData[]): RappelData[] {
    const dataRestreinte: RappelData[] = []
    for (let i = 0; i < 10; i++) {
      data[i] ? dataRestreinte.push(data[i]) : null;
    }
    return dataRestreinte;
  }

  const creerRappel = (date: string, commentaire: string) => {

    console.log("creer rappel : ajouter clické");

    if (rappelCreation) {
      console.log('com', commentaire);
      console.log('date', date);
      axios
        .post('api/rappels', { effectue: false, date: date, commentaire: commentaire })
        .then(response => {
          console.log("Réponse de l'API :", response.data);
          Swal.fire({
            text: "Rappel ajouté avec succès.📅",
            icon: 'success',
            timer: 2000
          })
        }
        )
        .catch(error => {
          if (error.response) {
            console.log('Erreur de réponse du serveur :', error.response.data);
            console.log('Statut de la réponse du serveur :', error.response.status);
          } else if (error.request) {
            console.log('Aucune réponse reçue du serveur');
          } else {
            console.log('Erreur de configuration de la requête :', error.message);
          }
          console.log('Erreur complète :', error.config);
        })
    }

  }



  function handleClick() {
    console.log("ajouter un rappel clické");

    setRappelCreation(true);


  }







  return (
    <Row style={{ height: '80vh' }}>
      <Col md="4" className="pad" style={{ backgroundColor: '##c6c6c6' }}>


        {isMed && (
          rappels ? (
            <div className='rappels-list'>
              <h2 className='rappels-title'> Pour ne rien oublier :</h2>
              <Rappel
                rappels={restreindreRappels10(trierParDate(rappels))}
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

        <Button onClick={handleClick}>
          Ajouter un rappel
        </Button>
        {rappelCreation && (
          <FormRappel
            submitRappel={creerRappel}
            date={dateFormatted} />
        )}

      </Col>
    </Row>
  );
};

export default Home;
