Cypress.Commands.add('login', () => {
	cy.visit('/login');
	cy.get('input[name="email"]').type('validemail@example.com');
	cy.get('input[name="password"]').type('password');
	cy.get('button[type="submit"]').click();
});

Cypress.Commands.add('loginWithSession', (username, password) => {
	cy.session(username, () => {
		cy.visit('/login');
		cy.get('input[name="email"]').type('test@test.test');
		cy.get('input[name="password"]').type('password');
		cy.get('button[type="submit"]').click();
	},
  )
})

Cypress.Commands.add('register', (username) => {
	cy.session(username, () => {

    cy.visit('/register');
    cy.get('[name="firstName"]').type('John');
    cy.get('[name="lastName"]').type('Doe');
    cy.get('[name="email"]').type('john.doe@exReg.com');
    cy.get('[name="password"]').type('password123');
    cy.get('[name="confirmPassword"]').type('password123');
		cy.get('.register').click();

		cy.intercept('POST', '**/api/users', {
      statusCode: 200,
      body: { success: true }
    }).as('registerUser');
    cy.intercept('POST', '**/api/login', {
      statusCode: 200,
      body: { token: 'validtoken' }
    }).as('login')
    cy.intercept('GET', '**/api/check_token', {
      statusCode: 200,
      body: { token: 'validtoken' }
    }).as('checkToken');

    cy.wait('@registerUser' && '@login' && '@checkToken').then(() => {
			cy.url().should('include', '/config');
    });
	})
});
