describe('Configuration', () => {
  
  beforeEach (() => {
    cy.register('username', 'password');

    cy.intercept('GET', '**/api/check_token', {
      statusCode: 200,
      body: { token: 'validtoken' }
    }).as('checkToken')

    window.localStorage.setItem('registering', true);
    cy.visit('/uploadFileConfig');
    cy.wait('@checkToken').then(() => {
      cy.url().should('include', '/uploadFileConfig');
    });
  })

  it('should display correct title', () => {
    cy.contains('h1', 'Konfigurering');
  });
  
  it('Hopp over navigate to goalConfig', () => {    
    cy.get('.basicButton').contains('Hopp over').click();
    cy.wait('@checkToken').then(() => {
      cy.url().should('include', '/goalConfig');
    });
  }); 
});