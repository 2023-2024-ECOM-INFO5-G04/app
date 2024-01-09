import React, {useEffect, useState} from 'react';
import 'app/TabbedAlerts.scss';
import {DropdownItem, DropdownMenu, DropdownToggle, UncontrolledDropdown} from "reactstrap";
import axios from "axios";
import { Link } from 'react-router-dom';

// A noter:  j'ai un attribu "type", ici il n'y a que denutrition. Il est laissé volontairement car il pourrait permettre de faire d'autres menu déroulant a l'avenir (alerte concernant des infos autres).

const TabbedAlerts = () => {
  const [alerts, setAlerts] = useState({
    denutrition: [],
    // { message: 'Error 1', clicked: false, id : 0 }, { message: 'Error 2', clicked: false, id : 0 }
  });
    const fetchPatientsWithAlerts = async () => {
      try {
        const response = await axios.get('api/patients/');
        const patientsWithAlerts = response.data.filter(patient => patient.alerte !== null && patient.alerte.denutrition === true);

        const infoAlerts = patientsWithAlerts.map(patient => ({
          message: `Le patient ${patient.nom} est en situation de dénutrition`,
          clicked: patient.alerte.consulte,
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

  useEffect(() => {
    fetchPatientsWithAlerts();
  }, []);

  // Permet la mise a jour régulière de la liste des alerte
  useEffect(() => {
    const fetchDataInterval = setInterval(() => {
      fetchPatientsWithAlerts();
    }, 10000); // en milisecondes (ici ça donne 10 secondes)
    return () => {
      clearInterval(fetchDataInterval);
    };
  }, []);


// Fonction utilisable pour du test, permet l'ajout d'une alerte.
  const addAlert = (type, message) => {
    setAlerts((prevAlerts) => {
      return {
        ...prevAlerts,
        [type]: [...prevAlerts[type], message],
      };
    });
  };

// Fonction utilisable pour du test, permet d'enlever une alerte.
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

//Permet de gérer le click sur une alerte. Donc le changement de couleur et la redirection vers la page du patient en question.
  const handleAlertClick = async (type, index) => {
    const updatedAlerts = { ...alerts }; //Necessité de updatedAlerts car il ne faut pas modifier directement alerts
    if (!updatedAlerts[type][index].clicked) {
      //updatedAlerts[type][index].id.alerte = true;
      const data =
        {
          "id": updatedAlerts[type][index].id.alerte.id,
          "date": updatedAlerts[type][index].id.alerte.date,
          "commentaire": updatedAlerts[type][index].id.alerte.commentaire,
          "denutrition": updatedAlerts[type][index].id.alerte.denutrition,
          "severite": updatedAlerts[type][index].id.alerte.severite,
          "consulte": true
        }
      const response = await axios.patch(`/api/alertes/${updatedAlerts[type][index].id.alerte.id}`, data, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

    }
    setAlerts(updatedAlerts);
  };



  const [showPastille, setShowPastille] = useState(false);
  const [lastTaille, setLastTaille] = useState(0);

  const pastilleAlert = async () => {
    if (alerts.denutrition.length > lastTaille){
      setShowPastille(true);
      localStorage.setItem('showPastille', 'true');
    }
    if (alerts.denutrition.length < lastTaille) {
      setShowPastille(false);
      localStorage.setItem('showPastille', 'false');
    }
    setLastTaille(alerts.denutrition.length);
  }

  useEffect(() => {
    pastilleAlert()
  }, [alerts.denutrition.length]);

  useEffect(() => {
    const pastilleValue = localStorage.getItem('showPastille');
    if (pastilleValue === 'true'){
      setShowPastille(true);
    } else {
      setShowPastille(false);
    }
  }, []);

  /*
  useEffect(() => {
    const pastillereload = setInterval(() => {
      pastilleAlert()
    }, 10000); // en milisecondes (ici ça donne 10 secondes)
    return () => {
      clearInterval(pastillereload);
    };
  }, [alerts.denutrition.length]);
  */

  const handleDropdownToggle = () => {
    // Masquer la pastille lorsque le menu déroulant est ouvert
    setShowPastille(false);
  };


  const dropdownMenuDenutrition = (
      <UncontrolledDropdown onToggle={handleDropdownToggle}>
        <DropdownToggle caret>
          Denutrition {showPastille && <div className="indicator"></div>}
        </DropdownToggle>
        <DropdownMenu>
          {alerts.denutrition.map((denutrition, index) => {
            const patientId = denutrition.id; // Déclaration de patientId à l'intérieur de la fonction map

            return (
                <DropdownItem>
                  {}
                  <Link
                      className="dropdownitem-link"
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
    </div>
  );
};

export default TabbedAlerts;
