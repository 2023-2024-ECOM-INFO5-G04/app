import './task.css'
import React from "react";
import { Col, Button, Alert, InputGroup, InputGroupText } from "reactstrap";
import AssignTo from './assignto';
import { FormGroup, Input, Label } from 'reactstrap';
import { useState, useEffect } from 'react';
import { width } from '@fortawesome/free-solid-svg-icons/faCogs';

//TODO : 
// utiliser requete pour verifier soignant et service

//TODO : 
// récuperer les données de tout le form et les verifier 

//TODO :
// feedback en alerte quand on valide et quitte la page




export const Task = (props) => {
    const [printEndDate, setPrintEndDate] = useState(true);
    const [printFreq, setPrintFreq] = useState(true);
    let freq: number;

    const handleFreqChange = (e) => {
        freq = e.target.value;
        console.log(freq)
    };


    return (
        <Col className='backT'>
            <div className='headT'>
                <h3> Assigner une tache </h3>
            </div >
            <div className='bodyT'>

                <AssignTo />
            </div>
            <div className='bodyT'>

                <FormGroup className='date'>
                    date de début :
                    <br />
                    <Input
                        id="exampleDate"
                        name="date"
                        placeholder="date placeholder"
                        type="date"
                    />
                </FormGroup>
                <FormGroup className='date'
                    style={{ visibility: printEndDate ? "visible" : "hidden" }}>
                    date de fin :
                    <br />
                    <Input
                        id="exampleDate"
                        name="date"
                        placeholder="date placeholder"
                        type="date"
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
                        onChange={handleFreqChange} />

                </FormGroup>

                <FormGroup className='select'
                    style={{ visibility: printFreq ? "visible" : "hidden" }}
                >
                    <div className='text'
                    >
                        fois par
                    </div>

                    <Input
                        onChange={(e) => { console.log(e.target.value) }}
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
                    />
                </FormGroup>

                <div className='thirdColEnd'>

                    <Button onClick={props.handleClick}
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
