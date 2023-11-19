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

describe('Servicesoignant e2e test', () => {
  const servicesoignantPageUrl = '/servicesoignant';
  const servicesoignantPageUrlPattern = new RegExp('/servicesoignant(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const servicesoignantSample = {};

  let servicesoignant;
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
      body: {"nom":"Ngultrum Wooden","adresse":"Vaneau orchid Dinar","telephone":"+33 549112156"},
    }).then(({ body }) => {
      etablissement = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/servicesoignants+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/servicesoignants').as('postEntityRequest');
    cy.intercept('DELETE', '/api/servicesoignants/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/etablissements', {
      statusCode: 200,
      body: [etablissement],
    });

    cy.intercept('GET', '/api/soignants', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/taches', {
      statusCode: 200,
      body: [],
    });

  });
   */

  afterEach(() => {
    if (servicesoignant) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/servicesoignants/${servicesoignant.id}`,
      }).then(() => {
        servicesoignant = undefined;
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

  it('Servicesoignants menu should load Servicesoignants page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('servicesoignant');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Servicesoignant').should('exist');
    cy.url().should('match', servicesoignantPageUrlPattern);
  });

  describe('Servicesoignant page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(servicesoignantPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Servicesoignant page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/servicesoignant/new$'));
        cy.getEntityCreateUpdateHeading('Servicesoignant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', servicesoignantPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/servicesoignants',
          body: {
            ...servicesoignantSample,
            etablissement: etablissement,
          },
        }).then(({ body }) => {
          servicesoignant = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/servicesoignants+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [servicesoignant],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(servicesoignantPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(servicesoignantPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Servicesoignant page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('servicesoignant');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', servicesoignantPageUrlPattern);
      });

      it('edit button click should load edit Servicesoignant page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Servicesoignant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', servicesoignantPageUrlPattern);
      });

      it('edit button click should load edit Servicesoignant page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Servicesoignant');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', servicesoignantPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Servicesoignant', () => {
        cy.intercept('GET', '/api/servicesoignants/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('servicesoignant').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', servicesoignantPageUrlPattern);

        servicesoignant = undefined;
      });
    });
  });

  describe('new Servicesoignant page', () => {
    beforeEach(() => {
      cy.visit(`${servicesoignantPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Servicesoignant');
    });

    it.skip('should create an instance of Servicesoignant', () => {
      cy.get(`[data-cy="type"]`).type('sticky').should('have.value', 'sticky');

      cy.get(`[data-cy="nbsoignants"]`).type('de').should('have.value', 'de');

      cy.get(`[data-cy="etablissement"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        servicesoignant = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', servicesoignantPageUrlPattern);
    });
  });
});
