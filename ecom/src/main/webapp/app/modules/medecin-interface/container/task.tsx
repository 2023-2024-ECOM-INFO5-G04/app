import './task.css'
import React from "react";
import { Col, Button } from "reactstrap";
import AssignTo from './assignto';

export const Task = (props) => {
    return (
        <Col className='backT'>
            <div className='headT'>
                Assigner une tache
            </div >
            <div className='bodyT'>
                body1
                <AssignTo />
            </div>
            <div className='bodyT'>
                body2
            </div>
            <div className='bodyT'>
                body2
            </div>

            <div className='footT'>
                footer

                <Button onClick={props.handleClick}
                    className='retour-button'>
                    Retour
                </Button>
                <Button onClick={props.handleClick}
                    className='valider-button'>
                    Valider
                </Button>
            </div>

        </Col>
    )
};

export default Task;
