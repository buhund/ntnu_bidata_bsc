describe('Overview Page', () => {
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

        cy.intercept('GET', '**/api/overview/**', {
            statusCode: 200,
            body: {
                startDate: '2024-04-01',
                endDate: '2024-05-01',
                incomes: 5000,
                expenses: 3000,
                expensesByCategory: [1000, 500, 300, 800, 400, 100]
            }
        }).as('getOverviewData')
      
        cy.visit('/overview');
    });

    it('should display correct title', () => {
        cy.wait('@login' && '@checkToken').then( () => {
            cy.contains('h1', 'Oversikt');
        })
    });

    it('should verify expenses and income fields', () => {
        cy.wait('@login' && '@checkToken').then( () => {
            cy.get('input ,#expenses,#incomes').should('have.length', 4);
            cy.contains('p', 'Dine utgifter er:');
            cy.contains('p', 'Dine inntekter er:');
        })
    });

    it('should contain a navigation bar', () => {
        cy.wait('@login' && '@checkToken').then( () => {
            cy.get('.navbar').should('exist');
        })
    });

    it('should display the doughnut chart', () => {
        cy.get('#startDate').type('2024-04-01')
        cy.get('#endDate').type('2024-04-29')
        cy.get('.updateButton').click()

        cy.wait('@login' && '@checkToken' && '@getOverviewData').then( () => {
            cy.get('.doughnutChart').should('be.visible');
        })
    });

   it('should verify expenses and income values from backend', () => {
        cy.wait('@getOverviewData').then(() => {
            cy.get('.expenses')
                .should('be.visible')
            cy.get('.incomes')
                .should('be.visible')
        });
    });
});
