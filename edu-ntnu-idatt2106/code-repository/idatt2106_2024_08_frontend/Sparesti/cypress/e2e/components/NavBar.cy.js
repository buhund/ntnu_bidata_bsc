describe('Navigation bar', () => {
    beforeEach(() => {
        cy.visit('/home');
    });

    it('should change the path to profile', () => {
        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.get('#profileBtn').click();

        cy.wait('@checkToken').then(() => {
            cy.url().should('include', '/profile');
        });
    });

    it('should change the path to goals', () => {
        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.get('#goals').click();

        cy.wait('@checkToken').then(() => {
            cy.url().should('include', '/badges');
        });
    });

    it('should change the path to sparesti', () => {
        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.get('#sparesti').click();

        cy.wait('@checkToken').then(() => {
            cy.url().should('include', '/sparesti');
        });
    });

    it('should change the path to overview', () => {
        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.get('#overview').click();

        cy.wait('@checkToken').then(() => {
            cy.url().should('include', '/overview');
        });
    });

    it('should change the path to home', () => {
        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.get('#home').click();

        cy.wait('@checkToken').then(() => {
            cy.url().should('include', '/home');
        });
    });

});