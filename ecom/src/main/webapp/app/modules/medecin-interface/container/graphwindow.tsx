import React from "react";
import { Col, Button } from "reactstrap"
import "./graphwindow.css"

import LineChart from "app/modules/medecin-interface/graphe/graphe";


export const GraphWindow = (props) => {

    return (

        <Col className='back'>
            <div className='head'>
                <h2>Graphique</h2>
            </div >
            <div className='body'>
                <LineChart />
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