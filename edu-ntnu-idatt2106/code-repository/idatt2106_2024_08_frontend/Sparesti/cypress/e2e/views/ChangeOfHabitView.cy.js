describe('ChangeOfHabitConfiguration', () => {
  
  beforeEach (() => {
    cy.register('username', 'password');
 
    cy.intercept('GET', '**/api/check_token', {
      statusCode: 200,
      body: { token: 'validtoken' }
    }).as('checkToken')

    cy.intercept('GET', '**/api/users/*', {
      statusCode: 200,
      body: {
          firstName: 'John',
          lastName: 'Doe',
          email: 'john.doe@exReg.com',
          password: 'password123',
          knowledgeLevel: 'low',
          willingnessToHabitChangeLevel: 'low'
      }
    }).as('getUserData');

    cy.intercept('PUT', '**/api/users/*', {
      statusCode: 200,
      body: {
        firstName: 'John',
        lastName: 'Doe',
        email: 'john.doe@exReg.com',
        password: 'password123',
        knowledgeLevel: 'low',
        willingnessToHabitChangeLevel: 'low'
      }
    }).as('saveEditedUser')

    window.localStorage.setItem('registering', true);
    cy.visit('/changeOfHabitConfig');
    cy.wait('@checkToken' && '@getUserData').then(() => {
      cy.url().should('include', '/changeOfHabitConfig');
    });
  })

  it('should display correct title', () => {  
    cy.contains('h1', 'Konfigurering');
  }); 

  it('should change knowledge level to high', () => {    
    cy.get('.radio').contains('Veldig').click();
    cy.get(':radio').eq(2).should('be.checked');
  });

  it('NextPagebutton in ConfigurationView navigate to changeOfHabitConfigView', () => {    
    cy.get('.basicButton').contains('Neste').click();    
    cy.wait('@saveEditedUser' && '@checkToken').then(() => {
      cy.url().should('include', '/uploadFileConfig');
    });
    
  });
});