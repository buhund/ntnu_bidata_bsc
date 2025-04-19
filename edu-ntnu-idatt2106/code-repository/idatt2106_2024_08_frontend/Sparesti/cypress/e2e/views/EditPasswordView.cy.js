describe('EditPasswordView', () => {
    beforeEach(() => {
        cy.visit('/login');
        cy.get('[name=email]').type('validemail@example.com')
        cy.get('[name=password]').type('validpassword')

        cy.intercept('POST', '**/api/login', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('login')

        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        const firstName = 'John';
        const lastName = 'Doe';
        const email = 'johndoe@example.com';
        const password = 'securepassword';
        const knowledgeLevel = 'high';
        const willingnessToHabitChangeLevel = 'high';

        cy.intercept('GET', '**/api/users/*', {
            statusCode: 200,
            body: {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                knowledgeLevel: knowledgeLevel,
                willingnessToHabitChangeLevel: willingnessToHabitChangeLevel
            }
        }).as('users')

        cy.visit('/editPassword');
    });

    it('should be able change password and return to profile page', () => {
        const firstName = 'John';
        const lastName = 'Doe';
        const email = 'johndoe@example.com';
        const password = 'securepassword';
        const knowledgeLevel = 'high';
        const willingnessToHabitChangeLevel = 'high';

        cy.intercept('PUT', '**/api/users/*', {
            statusCode: 200,
            body: {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                knowledgeLevel: knowledgeLevel,
                willingnessToHabitChangeLevel: willingnessToHabitChangeLevel
            }
        }).as('changePassword')

        cy.get('input').eq(0).type('passord')
        cy.get('input').eq(1).type('passord')
        cy.get('#changePassword').click()

        cy.wait('@changePassword').then(() => {
            cy.url().should('contain','/profile')
        })
    })

    it('should tell the user if passwords are different', () => {
        cy.get('input').eq(0).type('passord')
        cy.get('input').eq(1).type('password')
        cy.get('#changePassword').click()

        cy.get('.error').should('contain', 'Passordene matcher ikke')
    })
})