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

describe('Etablissement e2e test', () => {
  const etablissementPageUrl = '/etablissement';
  const etablissementPageUrlPattern = new RegExp('/etablissement(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const etablissementSample = {};

  let etablissement;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/etablissements+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/etablissements').as('postEntityRequest');
    cy.intercept('DELETE', '/api/etablissements/*').as('deleteEntityRequest');
  });

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

  it('Etablissements menu should load Etablissements page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('etablissement');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Etablissement').should('exist');
    cy.url().should('match', etablissementPageUrlPattern);
  });

  describe('Etablissement page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(etablissementPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Etablissement page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/etablissement/new$'));
        cy.getEntityCreateUpdateHeading('Etablissement');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/etablissements',
          body: etablissementSample,
        }).then(({ body }) => {
          etablissement = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/etablissements+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [etablissement],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(etablissementPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Etablissement page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('etablissement');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);
      });

      it('edit button click should load edit Etablissement page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Etablissement');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);
      });

      it('edit button click should load edit Etablissement page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Etablissement');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);
      });

      it('last delete button click should delete instance of Etablissement', () => {
        cy.intercept('GET', '/api/etablissements/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('etablissement').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);

        etablissement = undefined;
      });
    });
  });

  describe('new Etablissement page', () => {
    beforeEach(() => {
      cy.visit(`${etablissementPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Etablissement');
    });

    it('should create an instance of Etablissement', () => {
      cy.get(`[data-cy="nom"]`).type('Movies Account virtual').should('have.value', 'Movies Account virtual');

      cy.get(`[data-cy="adresse"]`).type('iterate').should('have.value', 'iterate');

      cy.get(`[data-cy="telephone"]`).type('+33 291691801').should('have.value', '+33 291691801');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        etablissement = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', etablissementPageUrlPattern);
    });
  });
});
