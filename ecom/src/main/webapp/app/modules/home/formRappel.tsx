import React from "react";
import { Input, Button } from 'reactstrap';

import Swal from 'sweetalert2';

const FormRappel = (props) => {

    const dateOfToday = props.date;
    let validData: boolean = false;

    let commentaire: string;
    let date: string;


    const checkDate = (dateInput: string) => {

        console.log('checkDate');

        const dateInputMS = (new Date(dateInput)).getTime();
        const dateMS = Date.now();

        if (dateInputMS - dateMS > 0) { //valide
            date = dateInput;
            console.log('date valide');
        }
        else {
            date = null;
            Swal.fire({
                'icon': 'warning',
                'text': "Vous ne pouvez pas saisir de date antérieure à aujourd'hui."
            })
        }
    }

    const sendForm = () => {

        console.log('sendForm');
        if (date) {
            if (!commentaire) {
                Swal.fire({
                    icon: 'error',
                    text: 'Veuillez remplir le champ Description.'
                })
            }
            else {
                props.submitRappel(date, commentaire)
            }
        }
        else {
            Swal.fire({
                icon: 'error',
                text: 'Veillez saisir une date correcte.'

            })
        }


    }

    return (
        <div className="">
            <Input onChange={(e) => commentaire = e.target.value}
                placeholder="Description">
            </Input>
            <Input
                name="date"
                type="date"
                onChange={(e) => checkDate(e.target.value)}
            />
            <Button onClick={sendForm}>
                Ajouter
            </Button>
        </div>
    )
}

export default FormRappel;