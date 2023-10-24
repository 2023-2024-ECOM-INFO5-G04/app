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

describe('Notes e2e test', () => {
  const notesPageUrl = '/notes';
  const notesPageUrlPattern = new RegExp('/notes(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const notesSample = {};

  let notes;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/notes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/notes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/notes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (notes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/notes/${notes.id}`,
      }).then(() => {
        notes = undefined;
      });
    }
  });

  it('Notes menu should load Notes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('notes');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Notes').should('exist');
    cy.url().should('match', notesPageUrlPattern);
  });

  describe('Notes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(notesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Notes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/notes/new$'));
        cy.getEntityCreateUpdateHeading('Notes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', notesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/notes',
          body: notesSample,
        }).then(({ body }) => {
          notes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/notes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [notes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(notesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Notes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('notes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', notesPageUrlPattern);
      });

      it('edit button click should load edit Notes page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Notes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', notesPageUrlPattern);
      });

      it('edit button click should load edit Notes page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Notes');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', notesPageUrlPattern);
      });

      it('last delete button click should delete instance of Notes', () => {
        cy.intercept('GET', '/api/notes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('notes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', notesPageUrlPattern);

        notes = undefined;
      });
    });
  });

  describe('new Notes page', () => {
    beforeEach(() => {
      cy.visit(`${notesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Notes');
    });

    it('should create an instance of Notes', () => {
      cy.get(`[data-cy="commentaire"]`).type('harness Phased').should('have.value', 'harness Phased');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        notes = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', notesPageUrlPattern);
    });
  });
});
