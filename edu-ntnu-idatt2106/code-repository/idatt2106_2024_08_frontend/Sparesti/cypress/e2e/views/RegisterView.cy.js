describe('User Registration Form', () => {
    beforeEach(() => {
        cy.visit('/register');
    });

    it('should show form', () => {
        cy.get('form').should('be.visible');
    });

    it('should show errormessage', () => {
        cy.get('[name="firstName"]').type('123');
        cy.get('[name="lastName"]').type('456');
        cy.get('[name="email"]').type('invalid-email');
        cy.get('[name="password"]').type('short');
        cy.get('[name="confirmPassword"]').type('short');

        cy.contains('Fornavn kan bare inneholde bokstaver');
        cy.contains('Må være en gyldig epost');
    });

    it('Right information', () => {
        cy.get('[name="firstName"]').clear().type('John');
        cy.get('[name="lastName"]').clear().type('Doe');
        cy.get('[name="email"]').clear().type('john.doe@example.com');
        cy.get('[name="password"]').clear().type('password123');
        cy.get('[name="confirmPassword"]').clear().type('password123');

        cy.get('.error-message').should('not.exist');
    });

    it('should change the path', () => {
        cy.get('[name="firstName"]').type('John');
        cy.get('[name="lastName"]').type('Do');
        cy.get('[name="email"]').type('john.do@a.com');
        cy.get('[name="password"]').type('password123');
        cy.get('[name="confirmPassword"]').type('password123');

        cy.intercept('POST', '**/api/users', {
            statusCode: 200,
            body: { success: true }
        }).as('registerUser');

        cy.intercept('POST', '**/api/login', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('login')
        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.get('.register').click();

        cy.wait('@registerUser' && '@login' && '@checkToken').then(() => {
            cy.url().should('include', '/config');
        });
    });
});