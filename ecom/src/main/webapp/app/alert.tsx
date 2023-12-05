import React, {useEffect, useState} from 'react';
import 'app/TabbedAlerts.scss';
import {DropdownItem, DropdownMenu, DropdownToggle, UncontrolledDropdown} from "reactstrap";
import axios from "axios";

// Il faut harmoniser le CSS avec celui de la navbar.

const TabbedAlerts = () => {
  const [selectedTab, setSelectedTab] = useState('denutrition');
  const [alerts, setAlerts] = useState({
    denutrition: [{ message: 'Error 1', clicked: false }, { message: 'Error 2', clicked: false }],
    infos: [{ message: 'Tacos', clicked: false }, { message: 'kebab', clicked: false }],
  });

  useEffect(() => {
    const fetchPatientsWithAlerts = async () => {
      try {
        const response = await axios.get('api/patients/');
        const patientsWithAlerts = response.data.filter(patient => patient.alerte !== null);

        const infoAlerts = patientsWithAlerts.map(patient => ({
          message: `Patient ${patient.name} has an alert: ${patient.alerte}`,
          clicked: false,
        }));

        setAlerts(prevAlerts => ({
          ...prevAlerts,
          denutrition: infoAlerts,
        }));
      } catch (error) {
        console.error('Error fetching patients:', error);
      }
    };
    fetchPatientsWithAlerts();
  }, []);

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

  const handleAlertClick = (type, index) => {
    const updatedAlerts = { ...alerts };
    if (!updatedAlerts[type][index].clicked) {
      updatedAlerts[type][index].clicked = true;
    }
    setAlerts(updatedAlerts);
  };

  return (
    <div className="tabbed-alerts-container">
      {/* Menu pour les dénutrition */}
      <UncontrolledDropdown>
        <DropdownToggle caret>
          Denutrition
        </DropdownToggle>
        <DropdownMenu>
          {alerts.denutrition.map((denutrition, index) => (
            <DropdownItem
              key={index}
              onClick={() => handleAlertClick('denutrition', index)}
              style={{ color: denutrition.clicked ? 'black' : 'red' }}
            >
              {denutrition.message}
            </DropdownItem>
          ))}
        </DropdownMenu>
      </UncontrolledDropdown>

      {/* Menu pour les informations */}
      <UncontrolledDropdown>
        <DropdownToggle caret>
          Infos
        </DropdownToggle>
        <DropdownMenu>
          {alerts.infos.map((infos, index) => (
            <DropdownItem
              key={index}
              onClick={() => handleAlertClick('infos', index)}
              style={{ color: infos.clicked ? 'black' : 'red' }}
            >
              {infos.message}
            </DropdownItem>
          ))}
        </DropdownMenu>
      </UncontrolledDropdown>
    </div>
  );
};

export default TabbedAlerts;
