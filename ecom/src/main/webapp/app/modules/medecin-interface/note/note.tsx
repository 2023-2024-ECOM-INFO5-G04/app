import React, { useState } from 'react';
import { Card, CardTitle, CardSubtitle, CardText, Button, Input } from 'reactstrap';
import './note.css';
import axios from 'axios';


export const Note = (props) => {
  const [contenu, setContenu] = useState(props.note.commentaire);
  const [contenuModifiable, setContenuModifiable] = useState('');
  const [estEnEdition, setEstEnEdition] = useState(false);

  const name = props.patient.nom;
  const noteId = props.note.id;
  console.log('noteID', noteId);


  const urlNotePatch = 'api/notes/' + noteId + '/commentaire?commentaire=';
  //normalement les données de mise à jour sont dans le corps pour une requête patch

  const modifierContenu = () => {
    setContenuModifiable(contenu);
    setEstEnEdition(true);
  };

  const sauvegarderContenuModifie = () => {
    setContenu(contenuModifiable);
    setEstEnEdition(false);
    const urlRequest = urlNotePatch + contenuModifiable;

    axios.patch(urlRequest)
      .then(response => {
        console.log('Réponse de la requête PATCH :', response.data);
      })
      .catch(error => {
        console.error('Erreur lors de la requête PATCH :', error);
      });
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
