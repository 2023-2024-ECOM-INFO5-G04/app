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

describe('Suividonnees e2e test', () => {
  const suividonneesPageUrl = '/suividonnees';
  const suividonneesPageUrlPattern = new RegExp('/suividonnees(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const suividonneesSample = {"idSD":29566,"date":"2023-10-24"};

  let suividonnees;
  // let patient;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/patients',
      body: {"idP":73604,"nomP":"Developpeur solid","prenomP":"relationships access","age":56719,"datearrivee":"2023-10-24","poidsactuel":97443,"albumine":4352,"taille":87134},
    }).then(({ body }) => {
      patient = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/suividonnees+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/suividonnees').as('postEntityRequest');
    cy.intercept('DELETE', '/api/suividonnees/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/patients', {
      statusCode: 200,
      body: [patient],
    });

  });
   */

  afterEach(() => {
    if (suividonnees) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/suividonnees/${suividonnees.id}`,
      }).then(() => {
        suividonnees = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
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
   */

  it('Suividonnees menu should load Suividonnees page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('suividonnees');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Suividonnees').should('exist');
    cy.url().should('match', suividonneesPageUrlPattern);
  });

  describe('Suividonnees page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(suividonneesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Suividonnees page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/suividonnees/new$'));
        cy.getEntityCreateUpdateHeading('Suividonnees');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', suividonneesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/suividonnees',
          body: {
            ...suividonneesSample,
            patient: patient,
          },
        }).then(({ body }) => {
          suividonnees = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/suividonnees+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [suividonnees],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(suividonneesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(suividonneesPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Suividonnees page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('suividonnees');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', suividonneesPageUrlPattern);
      });

      it('edit button click should load edit Suividonnees page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Suividonnees');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', suividonneesPageUrlPattern);
      });

      it('edit button click should load edit Suividonnees page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Suividonnees');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', suividonneesPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Suividonnees', () => {
        cy.intercept('GET', '/api/suividonnees/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('suividonnees').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', suividonneesPageUrlPattern);

        suividonnees = undefined;
      });
    });
  });

  describe('new Suividonnees page', () => {
    beforeEach(() => {
      cy.visit(`${suividonneesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Suividonnees');
    });

    it.skip('should create an instance of Suividonnees', () => {
      cy.get(`[data-cy="idSD"]`).type('80880').should('have.value', '80880');

      cy.get(`[data-cy="date"]`).type('2023-10-23').blur().should('have.value', '2023-10-23');

      cy.get(`[data-cy="poids"]`).type('80781').should('have.value', '80781');

      cy.get(`[data-cy="massecorporelle"]`).type('50635').should('have.value', '50635');

      cy.get(`[data-cy="quantitepoidsaliments"]`).type('73158').should('have.value', '73158');

      cy.get(`[data-cy="quantitecaloriesaliments"]`).type('63219').should('have.value', '63219');

      cy.get(`[data-cy="patient"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        suividonnees = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', suividonneesPageUrlPattern);
    });
  });
});
