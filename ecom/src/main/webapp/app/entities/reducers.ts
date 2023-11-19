import etablissement from 'app/entities/etablissement/etablissement.reducer';
import servicesoignant from 'app/entities/servicesoignant/servicesoignant.reducer';
import soignant from 'app/entities/soignant/soignant.reducer';
import administrateur from 'app/entities/administrateur/administrateur.reducer';
import tache from 'app/entities/tache/tache.reducer';
import medecin from 'app/entities/medecin/medecin.reducer';
import rappel from 'app/entities/rappel/rappel.reducer';
import compte from 'app/entities/compte/compte.reducer';
import patient from 'app/entities/patient/patient.reducer';
import alerte from 'app/entities/alerte/alerte.reducer';
import suividonnees from 'app/entities/suividonnees/suividonnees.reducer';
import notes from 'app/entities/notes/notes.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  etablissement,
  servicesoignant,
  soignant,
  administrateur,
  tache,
  medecin,
  rappel,
  compte,
  patient,
  alerte,
  suividonnees,
  notes,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
