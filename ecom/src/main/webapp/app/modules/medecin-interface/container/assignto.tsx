import { Input, FormGroup, Label, FormFeedback } from "reactstrap"
import React, { useState } from "react"
import Soignant from "app/entities/soignant/soignant"
import "./assignto.css"

const AssignTo = (props) => {

    const [selectedValue, setSelectedValue] = useState('soignant');
    const [nameIsValid, setNameIsValid] = useState(null);

    const handleSelectionChange = (e) => {
        setSelectedValue(e.target.value)
        console.log(selectedValue)
    };

    const handleNameChange = (e) => {

        console.log(e.target.value)
        if (e.target.value.length < 4) {
            setNameIsValid(false);
        }
        else { setNameIsValid(true) }
    };

    const vFeedback =
        <FormFeedback valid>
            Ce nom fait bien partie du registre
        </FormFeedback>
        ;

    const iFeedBack =
        <FormFeedback >
            Ce nom ne fait pas partie du registre
        </FormFeedback>
        ;

    return (
        <div className="assignation">

            <FormGroup tag='fieldset' className="radioGroup">
                <div className="dansRadioGroup">
                <FormGroup check >
                    <Label check>
                        <Input type="radio" name="radio1" value='soignant' checked={selectedValue == 'soignant'} onChange={handleSelectionChange} />
                        Soignant
                    </Label>
                </FormGroup>
                <FormGroup check id='service' className="radio">

                    <Label check>
                        <Input type="radio" name="radio2" value='service' checked={selectedValue == 'service'} onChange={handleSelectionChange} />
                        Service
                    </Label>

                </FormGroup>
                </div>
            </FormGroup>

            <FormGroup className="inputName" >
                <Input
                    onChange={handleNameChange}
                    placeholder="Renseignez le nom du soignant ou du service" />
                {nameIsValid ? vFeedback : iFeedBack}
            </FormGroup>
        </div>

    )

}

export default AssignTo