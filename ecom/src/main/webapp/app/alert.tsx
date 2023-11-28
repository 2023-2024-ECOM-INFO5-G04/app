import React, { useState } from 'react';
import 'app/TabbedAlerts.scss';
import {translate} from "react-jhipster";
import EntitiesMenuItems from "app/entities/menu";
import {NavDropdown} from "app/shared/layout/menus/menu-components";
import {DropdownItem, DropdownMenu, DropdownToggle, UncontrolledDropdown} from "reactstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

// Il faut harmoniser le CSS avec celui de la navbar.

const TabbedAlerts = () => {
  const [selectedTab, setSelectedTab] = useState('denutrition');
  const [alerts, setAlerts] = useState({
    denutrition: ["tacos"],
    infos: ["macdo"],
  });

  const handleTabChange = (tab) => {
    setSelectedTab(tab);
  };

  const addAlert = (type, message) => {
    setAlerts((prevAlerts) => {
      return {
        ...prevAlerts,
        [type]: [...prevAlerts[type], message],
      };
    });
  };

  const removeAlert = (type, index) => {
    setAlerts((prevAlerts) => {
      const updatedAlerts = [...prevAlerts[type]];
      updatedAlerts.splice(index, 1);
      return {
        ...prevAlerts,
        [type]: updatedAlerts,
      };
    });
  };

  /*
  return (
    <div className="tabbed-alerts-container">
      <div className="tabs">
        <button className={selectedTab === 'denutrition' ? 'active' : ''} onClick={() => handleTabChange('denutrition')}>
          Denutrition
        </button>
        <button className={selectedTab === 'infos' ? 'active' : ''} onClick={() => handleTabChange('infos')}>
          Infos
        </button>
      </div>

      <div className="alert-container">
        {alerts[selectedTab].map((alert, index) => (
          <div key={index} className={`alert ${selectedTab}`}>
            <span>{alert}</span>
            <button onClick={() => removeAlert(selectedTab, index)}>Close</button>
          </div>
        ))}
      </div>

      <div className="button-container">
        <button onClick={() => addAlert(selectedTab, `New ${selectedTab.slice(0, -1)} alert`)}>Add Alert</button>
      </div>
    </div>
  );*/
  return (
    <div className="tabbed-alerts-container">
      {/* Menu pour les dénutrition */}
      <UncontrolledDropdown>
        <DropdownToggle caret>
          Denutrition
        </DropdownToggle>
        <DropdownMenu>
          {alerts.denutrition.map((denutrition, index) => (
            <DropdownItem key={index}>{denutrition}</DropdownItem>
          ))}
        </DropdownMenu>
      </UncontrolledDropdown>

      {/* Menu pour les informations */}
      <UncontrolledDropdown>
        <DropdownToggle caret>
          Infos
        </DropdownToggle>
        <DropdownMenu>
          {alerts.infos.map((info, index) => (
            <DropdownItem key={index}>{info}</DropdownItem>
          ))}
        </DropdownMenu>
      </UncontrolledDropdown>
    </div>
  );
};

export default TabbedAlerts;
