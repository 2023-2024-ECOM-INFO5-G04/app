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

    const [rappels, setRappels] = useState<RappelData[]>(props.rappels || []);
    return (
        <div style={{width:'80%'}}>

            <UncontrolledAccordion

                open={[
                    
                ]}
                stayOpen
            >
                {rappels.map(rappel => (
                    
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