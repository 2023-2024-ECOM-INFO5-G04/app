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

describe('Rappel e2e test', () => {
  const rappelPageUrl = '/rappel';
  const rappelPageUrlPattern = new RegExp('/rappel(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const rappelSample = {};

  let rappel;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/rappels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/rappels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/rappels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (rappel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/rappels/${rappel.id}`,
      }).then(() => {
        rappel = undefined;
      });
    }
  });

  it('Rappels menu should load Rappels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('rappel');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Rappel').should('exist');
    cy.url().should('match', rappelPageUrlPattern);
  });

  describe('Rappel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(rappelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Rappel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/rappel/new$'));
        cy.getEntityCreateUpdateHeading('Rappel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', rappelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/rappels',
          body: rappelSample,
        }).then(({ body }) => {
          rappel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/rappels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [rappel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(rappelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Rappel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('rappel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', rappelPageUrlPattern);
      });

      it('edit button click should load edit Rappel page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Rappel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', rappelPageUrlPattern);
      });

      it('edit button click should load edit Rappel page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Rappel');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', rappelPageUrlPattern);
      });

      it('last delete button click should delete instance of Rappel', () => {
        cy.intercept('GET', '/api/rappels/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('rappel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', rappelPageUrlPattern);

        rappel = undefined;
      });
    });
  });

  describe('new Rappel page', () => {
    beforeEach(() => {
      cy.visit(`${rappelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Rappel');
    });

    it('should create an instance of Rappel', () => {
      cy.get(`[data-cy="date"]`).type('2023-11-06').blur().should('have.value', '2023-11-06');

      cy.get(`[data-cy="commentaire"]`).type('protocol Rubber functionalities').should('have.value', 'protocol Rubber functionalities');

      cy.get(`[data-cy="effectue"]`).should('not.be.checked');
      cy.get(`[data-cy="effectue"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        rappel = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', rappelPageUrlPattern);
    });
  });
});
