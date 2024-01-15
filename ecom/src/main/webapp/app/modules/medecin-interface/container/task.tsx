import './task.css'
import React from "react";
import { Col, Button, Alert, InputGroup, InputGroupText } from "reactstrap";
import AssignTo from './assignto';
import { FormGroup, Input, Label } from 'reactstrap';
import { useState, useEffect } from 'react';
import { useAppSelector } from 'app/config/store';
import axios from 'axios';
import Swal from 'sweetalert2';


export const Task = (props) => {
    const [printEndDate, setPrintEndDate] = useState(true);
    const [printFreq, setPrintFreq] = useState(true);

    const authentication = useAppSelector(state => state.authentication);
    const userID = authentication ? authentication.account.id : null;


    const patientId = props.patient.id;
    let medecinId;



    let target: string;
    let targetType: boolean = true;

    let dateDeb: string;
    let dateFin: string;

    let freq: number;
    let nbJour: number;

    let commentaire: string;

    useEffect(() => {
        axios
            .get('api/medecins/user/' + userID)
            .then(response => {
                medecinId = response.data.id;
            })
            .catch(error => {
                console.error('Erreur lors de la requête pour l EPA :', error);
                return null;
            });
    }, []);

    useEffect(() => {
        if (!printEndDate) {
            dateFin = null;
        }
    }, [printEndDate])

    useEffect(() => {
        if (!printFreq) {
            freq = null;
        }
    }, [printFreq])


    const getTarget = (e) => {
        target = e;
    }

    const getTypeTarget = (e) => {
        if (e == 'soignant') {
            targetType = true;
            return
        }
        if (e == 'service') {
            targetType = false;
            return
        }
        else {
            console.log('erreur radio bouton');
        }
    };

    const getDateDebut = (e) => {
        dateDeb = e;
    };

    const getDateFin = (e) => {
        dateFin = e;
    };

    const getFreq = (e) => {
        freq = e.target.value;
    };

    const getPeriode = (e) => {
        if (e == 'jour') {
            nbJour = 1;
            return;
        }
        if (e == 'semaine') {
            nbJour = 7;
            return;
        }
        if (e == 'mois') {
            nbJour = 30;
            return;
        }
        if (e == 'an') {
            nbJour = 364;
            return;
        }
        console.log('erreur frequence');
    }

    const getCommentaire = (e) => {
        commentaire = e;
        console.log(commentaire);
    }

    const submitTask = () => {
        console.log("submitted");

        if (medecinId == null) {
            msgError('Une erreur est survenue, veuillez réessayer.');
            return;
        }

        if (patientId == null) {
            msgError('Une erreur est survenue, veuillez réessayer.');
            return;
        }

        if (target == null || target == '') {
            msgError('nom du patient ou du service invalide');
            return;
        }
        if (targetType == null) {
            msgError('Veuillez selectionner une option : soignant/service');
            return;
        }

        if (dateDeb == null || dateDeb == '') {
            msgError('Veuillez renseigner une date de début');
            return;
        }

        if (commentaire == null || commentaire ==''){
            msgError('Veuillez décrire la tache à effectuer');
            return;
        }

        let url = 'api/taches/form?medecin=' + medecinId + '&patient=' + patientId + '&soignant=' + targetType + '&nom=' + target + '&debut=' + dateDeb;

        if (dateFin) {
            url += '&fin=' + dateFin;
        }
        if (freq) {
            url += '&frequence=' + freq;
        }
        if (nbJour) {
            url += '&joursperiode=' + nbJour;
        }
        if (commentaire) {
            url += '&commentaire=' + commentaire;
        }
        
        axios.post(url)
            .then(response => {
                msgSucces('La tache a bien été assignée');
            })
            .catch(error => {
                console.error('Erreur lors de la requête PATCH :', error);
                msgError('Un problème est survenu, êtes-vous sûr que ce nom existe ?');
            });
    }

   

    const msgError = (msg: string) => {
        Swal.fire({
            text: msg,
            icon: 'error'
        })
    };

    const msgSucces = (msg: string) => {
        Swal.fire({
            text: msg,
            icon: 'success',
            timer: 1000

        })
    };


    return (
        <Col className='backT'>
            <div className='headT'>
                <h3> Assigner une tache </h3>
            </div >
            <div className='bodyT'>

                <AssignTo
                    getTypeTarget={getTypeTarget}
                    getTarget={getTarget} />
            </div>
            <div className='bodyT'>

                <FormGroup className='date'>
                    date de début :
                    <br />
                    <Input
                        name="date-debut"
                        type="date"
                        onChange={(e) => getDateDebut(e.target.value)}
                    />
                </FormGroup>
                <FormGroup className='date'
                    style={{ visibility: printEndDate ? "visible" : "hidden" }}>
                    date de fin :
                    <br />
                    <Input
                        name="date-fin"
                        type="date"
                        onChange={(e) => getDateFin(e.target.value)}
                    />
                </FormGroup>
                <FormGroup check
                    className='thirdCol'>
                    <Input onChange={(e) => { setPrintEndDate(!printEndDate) }}
                        id="exampleCheck"
                        name="check"
                        type="checkbox"
                    />
                    <Label
                        check
                        for="exampleCheck"
                    >
                        Sans fin
                    </Label>
                </FormGroup>
            </div>
            <div className='bodyT'>
                <h5 className='freq-tittle'> Fréquence :</h5>
                <FormGroup
                    className='freq'
                    style={{ visibility: printFreq ? "visible" : "hidden" }}>

                    <Input
                        type='number'
                        onChange={getFreq} />

                </FormGroup>

                <FormGroup className='select'
                    style={{ visibility: printFreq ? "visible" : "hidden" }}
                >
                    <div className='text'
                    >
                        fois par
                    </div>

                    <Input
                        onChange={(e) => { getPeriode(e.target.value) }}
                        id="exampleSelect"
                        name="select"
                        type="select"
                    >
                        <option>
                            jour
                        </option>
                        <option>
                            semaine
                        </option>
                        <option>
                            mois
                        </option>
                        <option>
                            an
                        </option>
                    </Input>
                </FormGroup>
                <FormGroup check
                    className='thirdCol'>
                    <Input onChange={(e) => { setPrintFreq(!printFreq) }}
                        id="exampleCheck"
                        name="check"
                        type="checkbox"
                    />
                    <Label
                        check
                        for="exampleCheck"
                    >
                        Unique
                    </Label>
                </FormGroup>


            </div>

            <div className='footT'>

                <FormGroup className='commentaire'>

                    <Input
                        style={{ height: '80%' }}
                        id="exampleText"
                        name="text"
                        type="textarea"
                        placeholder='Ajoutez un commentaire'
                        onChange={(e) => { getCommentaire(e.target.value) }}
                    />
                </FormGroup>

                <div className='thirdColEnd'>

                    <Button onClick={submitTask}
                        className='valider-button'
                        color='success'>
                        Valider
                    </Button>
                    <Button onClick={props.handleClick}
                        className='retour-button'
                        color='secondary'>
                        Annuler
                    </Button>
                </div>
            </div>

        </Col>
    )
};

export default Task;
