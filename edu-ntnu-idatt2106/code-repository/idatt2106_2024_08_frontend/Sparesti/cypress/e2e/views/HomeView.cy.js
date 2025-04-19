describe('Home page', () => {
    beforeEach(() => {
        cy.login('username', 'password');

        cy.intercept('POST', '**/api/login', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('login')

        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.intercept('GET', '**/api/savings/*', {
            statusCode: 200,
            body: {
                savings: 500.0,
                savingsByCategory: [300.0,200.0,0.0,0.0,0.0,0.0],
                savingMessage: 'Det tilsvarer omtrent 25 liter diesel'
            }
        }).as("getSavings")

        cy.intercept('GET', '**/api/tips/*', {
            statusCode: 200,
            body: 'Dette er et tips'
        }).as('getTips')

        cy.visit('/home');
    });

    it('should display the tips container', () => {
        cy.wait('@login' && '@checkToken' && '@getTips' && '@getSavings').then( () => {
            cy.get('.tip-box').contains('TIPS!');
        })
    });

    it('should display a tips', () => {  
        cy.wait('@login' && '@checkToken' && '@getTips' && '@getSavings').then( () => {
            cy.get('.tip-box').contains('Dette er et tips');
        })
    });

    it('should show the total savings', () => {       
        cy.wait('@login' && '@checkToken' && '@getTips' && '@getSavings').then( () => {
            cy.get('.savings-circle').contains('500 KR');
        })
    });

    it('should show the savings message', () => {       
        cy.wait('@login' && '@checkToken' && '@getTips' && '@getSavings').then( () => {
            cy.get('.saved-message-box').contains('Det tilsvarer omtrent 25 liter diesel');
        })
    });
})