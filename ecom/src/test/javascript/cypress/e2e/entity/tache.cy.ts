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

describe('Tache e2e test', () => {
  const tachePageUrl = '/tache';
  const tachePageUrlPattern = new RegExp('/tache(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const tacheSample = {};

  let tache;
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
      body: {"nom":"syndicate Optimized Provence-Alpes-Côte","prenom":"b","age":45388,"datearrivee":"2023-11-06","poidsactuel":30667,"albumine":42073,"taille":54970,"sexe":"M","favori":true,"sarcopenie":true},
    }).then(({ body }) => {
      patient = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/taches+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/taches').as('postEntityRequest');
    cy.intercept('DELETE', '/api/taches/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/patients', {
      statusCode: 200,
      body: [patient],
    });

    cy.intercept('GET', '/api/servicesoignants', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/soignants', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/medecins', {
      statusCode: 200,
      body: [],
    });

  });
   */

  afterEach(() => {
    if (tache) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/taches/${tache.id}`,
      }).then(() => {
        tache = undefined;
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

  it('Taches menu should load Taches page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('tache');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Tache').should('exist');
    cy.url().should('match', tachePageUrlPattern);
  });

  describe('Tache page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(tachePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Tache page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/tache/new$'));
        cy.getEntityCreateUpdateHeading('Tache');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tachePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/taches',
          body: {
            ...tacheSample,
            patient: patient,
          },
        }).then(({ body }) => {
          tache = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/taches+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [tache],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(tachePageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(tachePageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Tache page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('tache');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tachePageUrlPattern);
      });

      it('edit button click should load edit Tache page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Tache');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tachePageUrlPattern);
      });

      it('edit button click should load edit Tache page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Tache');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tachePageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Tache', () => {
        cy.intercept('GET', '/api/taches/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('tache').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', tachePageUrlPattern);

        tache = undefined;
      });
    });
  });

  describe('new Tache page', () => {
    beforeEach(() => {
      cy.visit(`${tachePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Tache');
    });

    it.skip('should create an instance of Tache', () => {
      cy.get(`[data-cy="date"]`).type('2023-11-07').blur().should('have.value', '2023-11-07');

      cy.get(`[data-cy="commentaire"]`).type('Ball').should('have.value', 'Ball');

      cy.get(`[data-cy="effectuee"]`).should('not.be.checked');
      cy.get(`[data-cy="effectuee"]`).click().should('be.checked');

      cy.get(`[data-cy="patient"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        tache = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', tachePageUrlPattern);
    });
  });
});
