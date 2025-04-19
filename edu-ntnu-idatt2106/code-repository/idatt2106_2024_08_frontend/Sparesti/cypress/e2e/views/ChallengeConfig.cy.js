describe('challenge configuration', () => {
  beforeEach (() => {
    cy.register('username', 'password');
  
    cy.intercept('GET', '**/api/check_token', {
      statusCode: 200,
      body: { token: 'validtoken' }
    }).as('checkToken')

    cy.intercept('GET', '**/api/goals/user/*', {
      statusCode: 200,
      body: [{
        completed: false,
        endDate: '2029-11-11',
        id: '900',
        name: 'Ferie',
        progress: '0',
        startDate: '2024-07-07',
        target: '6666'
      }]
    }).as('getGoalData');

    cy.intercept('GET', '**/api/challenges/goal/*', {
      statusCode: 200,
      body: [{
        
      }]
    }).as('getChallengeData');

    window.localStorage.setItem('registering', true);
    cy.visit('/challengeConfig');
    cy.wait('@getGoalData' && '@checkToken' && '@getChallengeData').then(() => {
      cy.url().should('include', '/challengeConfig');
    });

  })

  it('should display correct title', () => {  
    cy.contains('h1', 'Konfigurering');
  });

  it('Hopp over navigate to home', () => {    
    cy.get('.basicButton').contains('Hopp over').click();
    
    cy.wait('@checkToken').then(() => {
      cy.url().should('include', '/home');
    });
  });  

  it('should be able to create new Challenge', () => {
    cy.intercept('GET', '**/api/challenges/generate/goal/*', {
      statusCode: 200,
      body: {
        id: null,
        name: 'bruke mindre penger',
        description: 'spar 111 kr',
        challengeType: 'SAVING_TARGET',
        target: '111',
        category: 'Dagligvarer',
        progress: '0',
        startDate: '2024-08-08',
        endDate: '2024-08-09',
        completed: false
      }
    }).as('getNewChallengeData');

    cy.intercept('POST', '**/api/challenges/goal/*', {
      statusCode: 200,
      body: {
        id: null,
        name: 'bruke mindre penger',
        description: 'spar 111 kr',
        challengeType: 'SAVING_TARGET',
        target: '111',
        category: 'Dagligvarer',
        progress: '0',
        startDate: '2024-08-08',
        endDate: '2024-08-09',
        completed: false
      }
    }).as('saveNewChallenge')

    cy.get('.basicButton').contains('Ny Utfordring').click()
    cy.wait('@checkToken'&&'@getNewChallengeData' ).then(() => {

      cy.get('.godta').contains('Godta').click()
      cy.wait('@saveNewChallenge').then(() => {
        cy.location('pathname').should('include', '/challengeConfig')
      })
    })      
  })
});