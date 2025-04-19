describe('Badges', () => {
  
  beforeEach (() => {
    cy.loginWithSession('username', 'password');

    cy.intercept('GET', '**/api/check_token', {
      statusCode: 200,
      body: { token: 'validtoken' }
    }).as('checkToken')
    cy.intercept('GET', '**/api/user/*/badges', {
      statusCode: 200,
      body : [{
        name: "Velkommen til Sparesti!",
        description: "Din sparereise starter her.",
        fileName: "laceBadge1",
        filePath: "/badgeassets/laceBadge1",
        userBadges: [{
          "id": 11,
          "achieved": true
      },
      {
          "id": 13,
          "achieved": true
      },]
      }]
    }).as('getBadges')
    cy.intercept('GET', '**/api/user/*/missing_badges', {
      statusCode: 200,
      body: [
        {
          name: "20 000kr Spart!",
          description: "Big Saver.",
          fileName: "ribbonBadge4",
          filePath: "/badgeassets/ribbonBadge4",
          userBadges: []
      },
      ]
    }).as('getMissingBadges')

    cy.visit('/badges');
    cy.wait('@getBadges' && '@getMissingBadges' && '@checkToken').then(() => {
      cy.url().should('include', '/badges');
    });
  })

  it('should display correct title', () => {
    cy.contains('h1', 'Badges');
  });
  
  it('Show uncompletet and completed Badges in list', () => {    
    cy.get('.badge-description').eq(0).contains('Velkommen til Sparesti! - Din sparereise starter her.')
    cy.get('.badge-description').eq(1).contains('20 000kr Spart! - Big Saver.')   
  });

  it('Show Badge in popup when clicked', () => {    
    cy.get('.badge-description').eq(0).contains('Velkommen til Sparesti! - Din sparereise starter her.').click();
    cy.get('h3').contains('Velkommen til Sparesti!')
  });
});