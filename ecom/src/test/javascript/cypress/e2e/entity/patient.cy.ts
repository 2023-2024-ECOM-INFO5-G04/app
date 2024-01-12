import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Patient e2e test', () => {
  const patientPageUrl = '/patient';
  const patientPageUrlPattern = new RegExp('/patient(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const patientSample = {};

  let patient;
  // let etablissement;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/etablissements',
      body: {"nom":"secondary Metal","adresse":"Fish Loan","telephone":"+33 306081569"},
    }).then(({ body }) => {
      etablissement = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/patients+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/patients').as('postEntityRequest');
    cy.intercept('DELETE', '/api/patients/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/alertes', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/notes', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/etablissements', {
      statusCode: 200,
      body: [etablissement],
    });

    cy.intercept('GET', '/api/suividonnees', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/taches', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/medecins', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/soignants', {
      statusCode: 200,
      body: [],
    });

  });
   */

  afterEach(() => {
    if (patient) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/patients/${patient.id}`,
      }).then(() => {
        patient = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (etablissement) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/etablissements/${etablissement.id}`,
      }).then(() => {
        etablissement = undefined;
      });
    }
  });
   */

  it('Patients menu should load Patients page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('patient');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Patient').should('exist');
    cy.url().should('match', patientPageUrlPattern);
  });

  describe('Patient page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(patientPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Patient page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/patient/new$'));
        cy.getEntityCreateUpdateHeading('Patient');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/patients',
          body: {
            ...patientSample,
            etablissement: etablissement,
          },
        }).then(({ body }) => {
          patient = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/patients+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [patient],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(patientPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(patientPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Patient page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('patient');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);
      });

      it('edit button click should load edit Patient page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Patient');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);
      });

      it('edit button click should load edit Patient page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Patient');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Patient', () => {
        cy.intercept('GET', '/api/patients/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('patient').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', patientPageUrlPattern);

        patient = undefined;
      });
    });
  });

  describe('new Patient page', () => {
    beforeEach(() => {
      cy.visit(`${patientPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Patient');
    });

    it.skip('should create an instance of Patient', () => {
      cy.get(`[data-cy="nom"]`).type('Generic').should('have.value', 'Generic');

      cy.get(`[data-cy="prenom"]`).type('Rand Avon Buckinghamshire').should('have.value', 'Rand Avon Buckinghamshire');

      cy.get(`[data-cy="age"]`).type('43559').should('have.value', '43559');

      cy.get(`[data-cy="datearrivee"]`).type('2023-11-06').blur().should('have.value', '2023-11-06');

      cy.get(`[data-cy="poidsactuel"]`).type('59033').should('have.value', '59033');

      cy.get(`[data-cy="albumine"]`).type('81481').should('have.value', '81481');

      cy.get(`[data-cy="taille"]`).type('34440').should('have.value', '34440');

      cy.get(`[data-cy="sexe"]`).select('M');

      cy.get(`[data-cy="favori"]`).should('not.be.checked');
      cy.get(`[data-cy="favori"]`).click().should('be.checked');

      cy.get(`[data-cy="sarcopenie"]`).should('not.be.checked');
      cy.get(`[data-cy="sarcopenie"]`).click().should('be.checked');

      cy.get(`[data-cy="absorptionreduite"]`).should('not.be.checked');
      cy.get(`[data-cy="absorptionreduite"]`).click().should('be.checked');

      cy.get(`[data-cy="agression"]`).should('not.be.checked');
      cy.get(`[data-cy="agression"]`).click().should('be.checked');

      cy.get(`[data-cy="etablissement"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        patient = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', patientPageUrlPattern);
    });
  });
});
