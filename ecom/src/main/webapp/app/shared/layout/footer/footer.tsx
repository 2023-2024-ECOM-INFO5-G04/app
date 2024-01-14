import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>
          {/* Ecom 2023-2024 : Clément, Jamile, Mathis, Michelle, Léa */}
          {/* Que les hommes et mes confrères m’accordent leur estime
si je suis fidèle à mes promesses; que je sois déshonoré(e) et méprisé(e)
si j’y manque. */}
          "Je promets et je jure d’être fidèle aux lois de l’honneur et de la probité."
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
