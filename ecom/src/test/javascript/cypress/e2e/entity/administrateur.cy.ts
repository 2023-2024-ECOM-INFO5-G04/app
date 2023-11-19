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

describe('Administrateur e2e test', () => {
  const administrateurPageUrl = '/administrateur';
  const administrateurPageUrlPattern = new RegExp('/administrateur(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const administrateurSample = {};

  let administrateur;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/administrateurs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/administrateurs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/administrateurs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (administrateur) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/administrateurs/${administrateur.id}`,
      }).then(() => {
        administrateur = undefined;
      });
    }
  });

  it('Administrateurs menu should load Administrateurs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('administrateur');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Administrateur').should('exist');
    cy.url().should('match', administrateurPageUrlPattern);
  });

  describe('Administrateur page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(administrateurPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Administrateur page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/administrateur/new$'));
        cy.getEntityCreateUpdateHeading('Administrateur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', administrateurPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/administrateurs',
          body: administrateurSample,
        }).then(({ body }) => {
          administrateur = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/administrateurs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [administrateur],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(administrateurPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Administrateur page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('administrateur');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', administrateurPageUrlPattern);
      });

      it('edit button click should load edit Administrateur page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Administrateur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', administrateurPageUrlPattern);
      });

      it('edit button click should load edit Administrateur page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Administrateur');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', administrateurPageUrlPattern);
      });

      it('last delete button click should delete instance of Administrateur', () => {
        cy.intercept('GET', '/api/administrateurs/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('administrateur').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', administrateurPageUrlPattern);

        administrateur = undefined;
      });
    });
  });

  describe('new Administrateur page', () => {
    beforeEach(() => {
      cy.visit(`${administrateurPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Administrateur');
    });

    it('should create an instance of Administrateur', () => {
      cy.get(`[data-cy="nom"]`).type('quantifying Tala bypass').should('have.value', 'quantifying Tala bypass');

      cy.get(`[data-cy="prenom"]`).type('Metal Franche-Comté Computers').should('have.value', 'Metal Franche-Comté Computers');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        administrateur = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', administrateurPageUrlPattern);
    });
  });
});
