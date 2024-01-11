import React, {useEffect, useState} from "react";
import { Col, Button } from "reactstrap"
import "./graphwindow.css"

import LineChart from "app/modules/medecin-interface/graphe/graphe";


export const GraphWindow = (props) => {
  const numPa = props.numPa;
    return (

        <Col className='back'>
            <div className='head'>
                <h2>Graphique</h2>
            </div >
            <div className='body'>
                <LineChart id={numPa}/>
            </div>
            <div className='foot'>

                <Button onClick={props.handleClick}
                    className="tache-button">
                    Ajouter une tache
                </Button>
            </div>

        </Col>)

}

export default GraphWindow;
