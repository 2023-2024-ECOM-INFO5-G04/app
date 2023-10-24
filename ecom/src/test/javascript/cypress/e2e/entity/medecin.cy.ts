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

describe('Medecin e2e test', () => {
  const medecinPageUrl = '/medecin';
  const medecinPageUrlPattern = new RegExp('/medecin(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const medecinSample = { idM: 76260 };

  let medecin;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/medecins+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/medecins').as('postEntityRequest');
    cy.intercept('DELETE', '/api/medecins/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (medecin) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/medecins/${medecin.id}`,
      }).then(() => {
        medecin = undefined;
      });
    }
  });

  it('Medecins menu should load Medecins page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('medecin');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Medecin').should('exist');
    cy.url().should('match', medecinPageUrlPattern);
  });

  describe('Medecin page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(medecinPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Medecin page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/medecin/new$'));
        cy.getEntityCreateUpdateHeading('Medecin');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', medecinPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/medecins',
          body: medecinSample,
        }).then(({ body }) => {
          medecin = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/medecins+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [medecin],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(medecinPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Medecin page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('medecin');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', medecinPageUrlPattern);
      });

      it('edit button click should load edit Medecin page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Medecin');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', medecinPageUrlPattern);
      });

      it('edit button click should load edit Medecin page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Medecin');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', medecinPageUrlPattern);
      });

      it('last delete button click should delete instance of Medecin', () => {
        cy.intercept('GET', '/api/medecins/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('medecin').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', medecinPageUrlPattern);

        medecin = undefined;
      });
    });
  });

  describe('new Medecin page', () => {
    beforeEach(() => {
      cy.visit(`${medecinPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Medecin');
    });

    it('should create an instance of Medecin', () => {
      cy.get(`[data-cy="idM"]`).type('29787').should('have.value', '29787');

      cy.get(`[data-cy="nomM"]`).type('Pants').should('have.value', 'Pants');

      cy.get(`[data-cy="prenomM"]`).type('generation hack').should('have.value', 'generation hack');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        medecin = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', medecinPageUrlPattern);
    });
  });
});
