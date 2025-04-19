describe('goalConfiguration and challengeConfig', () => {
  beforeEach (() => {
    cy.register('username', 'password');

    cy.intercept('POST', '**/api/users', {
      statusCode: 200,
      body: { success: true }
    }).as('registerUser');
  
    cy.intercept('GET', '**/api/check_token', {
      statusCode: 200,
      body: { token: 'validtoken' }
    }).as('checkToken')

    window.localStorage.setItem('registering', true);
    cy.visit('/goalconfig');
    cy.wait('@checkToken').then(() => {
      cy.url().should('include', '/goalconfig');
    });
  })

  it('should display correct title', () => {  
    cy.contains('h1', 'Konfigurering');
  });

  it('should be able to create new Goal', () => {
    const newName = 'Ferie';
    const newTarget = '666';
    const newDate = '2029-11-11';

      cy.intercept('POST', '**/api/goals/user/*', {
        statusCode: 200,
        body: {
          name: newName,
          target: newTarget,
          endDate: newDate
        }
      }).as('saveNewGoal')

      cy.get('input').eq(0).type(newName)
      cy.get('input').eq(1).type(newTarget)
      cy.get('input').eq(2).type(newDate)

      cy.get('.basicButton').contains('Neste').click()

      cy.wait('@saveNewGoal').then(() => {
        cy.url().should('include', '/challengeConfig');
      })   
  })

  it('Hopp over in ConfigurationView navigate to home', () => {    
    cy.get('.basicButton').contains('Hopp over').click();
    
    cy.wait('@checkToken').then(() => {
      cy.url().should('include', '/home');
    });
  });  
});