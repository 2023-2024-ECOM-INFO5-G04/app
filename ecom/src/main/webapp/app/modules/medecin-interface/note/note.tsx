import React, { useState } from 'react';
import { Card, CardTitle, CardSubtitle, CardText, Button, Input } from 'reactstrap';
import './note.css';

export const Note = (props) => {
  const [contenu, setContenu] = useState('Rien de remarquable');
  const [contenuModifiable, setContenuModifiable] = useState('');
  const [estEnEdition, setEstEnEdition] = useState(false);

  const name = props.patient.nom;

  const modifierContenu = () => {
    setContenuModifiable(contenu);
    setEstEnEdition(true);
  };

  const sauvegarderContenuModifie = () => {
    setContenu(contenuModifiable);
    setEstEnEdition(false);
  };

  const annulerModification = () => {
    setEstEnEdition(false);
  };

  return (
    <Card >
      <CardTitle tag="h5">{name}</CardTitle>
      <CardSubtitle tag="h6" className="mb-2 text-muted">
        Note personnelle
      </CardSubtitle>
      {estEnEdition ? (
        <>
          <Input type="textarea" value={contenuModifiable} onChange={e => setContenuModifiable(e.target.value)} />
          <Button className="bouton" onClick={sauvegarderContenuModifie}>Sauvegarder</Button>
          <Button className="bouton" onClick={annulerModification}>Annuler</Button>
        </>
      ) : (
        <>
          <CardText>{contenu}</CardText>
          <Button onClick={modifierContenu}>Éditer</Button>
        </>
      )}
    </Card>
  );
};

export default Note;
