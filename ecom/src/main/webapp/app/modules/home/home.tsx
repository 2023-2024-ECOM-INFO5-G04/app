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

  const [rappels, setRappels] = useState(() => {
    const storedRappels = localStorage.getItem('rappels');
    return storedRappels ? JSON.parse(storedRappels) : [];
  });

  useEffect(() => {
    if (rappels.length > 0) {
      localStorage.setItem('rappels', JSON.stringify(rappels));
    }
  }, [rappels]);

  const [rappelCreation, setRappelCreation] = useState(false);

  useEffect(() => {
    if (isMed) {
      axios
        .get('api/rappels')
        .then(response => {
          const resp: RappelData[] = donneesRappel(response.data);
          const rep: RappelData[] = restreindreRappels10(trierParDate(resp));
          if (rep.length > 0 && rep.length != rappels.length) {
            setRappels(rep);
          }
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
    [rappels]
  });

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
    if (rappelCreation) {
      axios
        .post('api/rappels', { effectue: false, date: date, commentaire: commentaire })
        .then(response => {
          Swal.fire({
            text: "Rappel ajouté avec succès.📅",
            icon: 'success',
            timer: 1000
          })
          setRappels([]);
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
    setRappelCreation(!rappelCreation);
  }

  return (
    <Row style={{ height: '80vh' }}>
      <Col md="4" className="pad" style={{ backgroundColor: '##c6c6c6' }}>
        {isMed ? (
          <div className='rappels-list'>
            <h2 className='rappels-title'> Pour ne rien oublier :</h2>
            <Rappel
              rappels={rappels}
            /> </div>) : (<div>
              <FontAwesomeIcon icon={faStethoscope} />
            </div>)
        }
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


      <Col md="4" className='col3'>
        {account?.login ? (
          <div className='identification'>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>

          </div>
        ) : (
          <div className='identification'>
            <Alert color="warning">
              Attention ! Vous n'êtes pas connecté.e.
            </Alert>
          </div>
        )}

        <div className='form-container'>
          {isMed && (<Button onClick={handleClick}
            color='dark'
            className='switch-bouton'>
            {rappelCreation ? 'Terminer' : 'Ajouter un rappel'}
          </Button>)}
          {rappelCreation && (
            <FormRappel
              submitRappel={creerRappel}
            />
          )}
        </div>
      </Col>

      
    </Row>
  );
};

export default Home;
