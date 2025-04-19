describe('Forgot Password Page', () => {
    beforeEach(() => {
        cy.visit('/forgotPassword')
    })

    it('displays the form correctly', () => {
        cy.contains('h1', 'Få nytt passord')
        cy.contains('.description', 'Skriv inn e-postadressen du registrerte deg med.')
        cy.get('input[name="email"]').should('exist')
        cy.get('button.sendNewPassword').should('exist')
    })

    it('requires email to be filled out', () => {
        cy.get('input[name="email"]').type('test')
        cy.get('input[name="email"]').clear()

        cy.contains('.error', 'Epost må fylles ut').should('exist')
    })

    it('validates email format', () => {
        cy.get('input[name="email"]').type('invalidemail')
        cy.contains('.error', 'Må være en gyldig epost').should('exist')
    })

    it('displays the form correctly when an email is sent successfully', () => {
        const validEmail = 'test@example.com';
        cy.get('input[name="email"]').type(validEmail)
        cy.intercept('POST', '**/api/forgot_password', {
            statusCode: 200
        })
        cy.get('button.sendNewPassword').click()
        cy.contains('p', `En epost med nytt passord er sendt til ${validEmail}`).should('exist')
    })

    it('displays the error message when submitting an unregistered email', () => {
        const validEmail = 'unregistered.email@example.com';
        cy.get('input[name="email"]').type(validEmail)
        cy.intercept('POST', '**/api/forgot_password', {
            statusCode: 404
        })
        cy.get('button.sendNewPassword').click()
        cy.contains('.error', 'Eposten er ikke registrert').should('exist')
        cy.contains('p', `En epost med nytt passord er sendt til ${validEmail}`).should('not.exist')
    })

})
