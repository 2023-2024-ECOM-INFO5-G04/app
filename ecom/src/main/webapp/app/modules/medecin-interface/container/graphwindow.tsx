import React from "react";
import { Col, Button } from "reactstrap"
import "./graphwindow.css"


export const GraphWindow = (props) => {

    return (

        <Col className='back'>
            <div className='head'>
                header
            </div >
            <div className='body'>
                body
            </div>
            <div className='foot'>
                footer

                <Button onClick={props.handleClick}
                className="valider-button">
                    Ajouter une tache
                </Button>
            </div>

        </Col>)

}

export default GraphWindow;