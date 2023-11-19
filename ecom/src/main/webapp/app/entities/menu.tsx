import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/etablissement">
        <Translate contentKey="global.menu.entities.etablissement" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/servicesoignant">
        <Translate contentKey="global.menu.entities.servicesoignant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/soignant">
        <Translate contentKey="global.menu.entities.soignant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/administrateur">
        <Translate contentKey="global.menu.entities.administrateur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tache">
        <Translate contentKey="global.menu.entities.tache" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/medecin">
        <Translate contentKey="global.menu.entities.medecin" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/rappel">
        <Translate contentKey="global.menu.entities.rappel" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/compte">
        <Translate contentKey="global.menu.entities.compte" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/patient">
        <Translate contentKey="global.menu.entities.patient" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/alerte">
        <Translate contentKey="global.menu.entities.alerte" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/suividonnees">
        <Translate contentKey="global.menu.entities.suividonnees" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/notes">
        <Translate contentKey="global.menu.entities.notes" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
