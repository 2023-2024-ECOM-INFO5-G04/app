import React, { useState } from 'react';
import {
    AccordionBody,
    AccordionHeader,
    AccordionItem,
    UncontrolledAccordion,
} from 'reactstrap';
import './home.scss';
import { RappelData } from '../medecin-interface/classes/rappels-class';


export const Rappel = props => {

    const raappels = props.rappels || [];

    return (
        <div style={{ width: '80%' }}>

            <UncontrolledAccordion

                open={[

                ]}
                stayOpen
            >
                {raappels.map(rappel => (

                    <AccordionItem key={rappel.id.toString()}>
                        <AccordionHeader targetId={rappel.id.toString()} className='rappels'>{rappel.date}</AccordionHeader>
                        <AccordionBody accordionId={rappel.id.toString()}>
                            {rappel.commentaire}
                        </AccordionBody>
                    </AccordionItem>
                ))}
            </UncontrolledAccordion>
        </div>
    );
}

export default Rappel;