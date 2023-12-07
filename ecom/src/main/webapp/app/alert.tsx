import React, {useEffect, useState} from 'react';
import 'app/TabbedAlerts.scss';
import {DropdownItem, DropdownMenu, DropdownToggle, UncontrolledDropdown} from "reactstrap";
import axios from "axios";
import { Link } from 'react-router-dom';

// Il faut harmoniser le CSS avec celui de la navbar.

const TabbedAlerts = () => {
  const [alerts, setAlerts] = useState({
    denutrition: [{ message: 'Error 1', clicked: false, id : 0 }, { message: 'Error 2', clicked: false, id : 0 }],
    infos: [{ message: 'Tacos', clicked: false }, { message: 'kebab', clicked: false }],
  });

  useEffect(() => {
    const fetchPatientsWithAlerts = async () => {
      try {
        const response = await axios.get('api/patients/');
        const patientsWithAlerts = response.data.filter(patient => patient.alerte !== null);

        const infoAlerts = patientsWithAlerts.map(patient => ({
          message: `Patient ${patient.nom} has an alert: ${patient.alerte}`,
          clicked: false,
          id : patient,
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

  const handleAlertClick = async (type, index) => {
    const updatedAlerts = { ...alerts };
    if (!updatedAlerts[type][index].clicked) {
      updatedAlerts[type][index].clicked = true;
    }
    setAlerts(updatedAlerts);
    try {
    const response = await axios.get(`api/patients/${alerts[type][index].id}`);
    const patientDetails = response.data;
    } catch (error) {
      console.error('Erreur lors de la récupération des détails du patient :', error);
    }
  };

  const dropdownMenuDenutrition = (
      <UncontrolledDropdown>
        <DropdownToggle caret>
          Denutrition
        </DropdownToggle>
        <DropdownMenu>
          {alerts.denutrition.map((denutrition, index) => {
            const patientId = denutrition.id; // Déclaration de patientId à l'intérieur de la fonction map

            return (
                <DropdownItem>
                  <Link
                      className="dropdown-item-link"
                      to="/patientdetails"
                      state={patientId}
                      key={index}
                      onClick={() => handleAlertClick('denutrition', index)}
                      style={{ color: denutrition.clicked ? 'black' : 'red' }}
                  >
                    {denutrition.message}
                  </Link>
                </DropdownItem>
            );
          })}
        </DropdownMenu>
      </UncontrolledDropdown>
  );

  return (
    <div className="tabbed-alerts-container">

      {dropdownMenuDenutrition}

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
