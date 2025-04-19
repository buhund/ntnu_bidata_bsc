describe('Go Back Button', () => {
    it('should navigate to /login from /forgotPassword when clicked', () => {
        cy.visit('/forgotPassword');
        cy.get('.goBackButton').click();
        cy.url().should('include', '/login');
    });

    it('should navigate to / from /login when clicked', () => {
        cy.visit('/login');
        cy.get('.goBackButton').click();
        cy.url().should('include', '/');
    });

    it('should navigate to / from /register when clicked', () => {
        cy.visit('/register');
        cy.get('.goBackButton').click();
        cy.url().should('include', '/');
    });
});
