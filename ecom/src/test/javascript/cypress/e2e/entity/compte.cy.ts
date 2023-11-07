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

describe('Compte e2e test', () => {
  const comptePageUrl = '/compte';
  const comptePageUrlPattern = new RegExp('/compte(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const compteSample = {};

  let compte;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/comptes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/comptes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/comptes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (compte) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/comptes/${compte.id}`,
      }).then(() => {
        compte = undefined;
      });
    }
  });

  it('Comptes menu should load Comptes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('compte');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Compte').should('exist');
    cy.url().should('match', comptePageUrlPattern);
  });

  describe('Compte page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(comptePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Compte page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/compte/new$'));
        cy.getEntityCreateUpdateHeading('Compte');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', comptePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/comptes',
          body: compteSample,
        }).then(({ body }) => {
          compte = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/comptes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [compte],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(comptePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Compte page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('compte');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', comptePageUrlPattern);
      });

      it('edit button click should load edit Compte page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Compte');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', comptePageUrlPattern);
      });

      it('edit button click should load edit Compte page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Compte');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', comptePageUrlPattern);
      });

      it('last delete button click should delete instance of Compte', () => {
        cy.intercept('GET', '/api/comptes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('compte').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', comptePageUrlPattern);

        compte = undefined;
      });
    });
  });

  describe('new Compte page', () => {
    beforeEach(() => {
      cy.visit(`${comptePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Compte');
    });

    it('should create an instance of Compte', () => {
      cy.get(`[data-cy="nomutilisateur"]`).type('Robust Balanced').should('have.value', 'Robust Balanced');

      cy.get(`[data-cy="motdepasse"]`).type('Practical Clothing').should('have.value', 'Practical Clothing');

      cy.get(`[data-cy="mail"]`).type('Ouzbékistan optical').should('have.value', 'Ouzbékistan optical');

      cy.get(`[data-cy="role"]`).select('Medecin');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        compte = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', comptePageUrlPattern);
    });
  });
});
