describe('Startpage choose login or register', () => {
    beforeEach(() => {
        cy.visit('/');
    });

    it('should change the path register', () => {
        cy.get('#register').click();
        cy.url().should('include', '/register');
    });

    it('should change the path to login', () => {
        cy.get('#login').click();
        cy.url().should('include', '/login');
    });
});