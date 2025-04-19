describe('Login form', () => {
    beforeEach(() => {
        cy.visit('/login')
    })

    it('displays error message for invalid email address', () => {
        cy.get('[name=email]').type('invalidemail')
        cy.contains('.error', 'Må være en gyldig epost')
    })

    it('displays error message for missing password', () => {
        cy.get('[name=password]').type('validpassword')
        cy.get('[name=password]').clear()
        cy.contains('.error', 'Passord må fylles ut')
    })

    it('displays error message for user not found', () => {
        cy.get('[name=email]').type('validemail@example.com')
        cy.get('[name=password]').type('validpassword')
        cy.intercept('POST', '**/api/login', {
            statusCode: 401,
            body: { message: 'User not found' }
        })
        cy.get('.login').click()
        cy.contains('.error', 'E-post adresse og/eller passordet er feil. Prøv igjen')
    })

    it('logs in the user with valid credentials', () => {
        cy.get('[name=email]').type('validemail@example.com')
        cy.get('[name=password]').type('validpassword')
        cy.intercept('POST', '**/api/login', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('login')
        cy.get('.login').click()

        cy.wait('@login').then( () => {
            cy.url().should('include', '/home')
        })
    })
})