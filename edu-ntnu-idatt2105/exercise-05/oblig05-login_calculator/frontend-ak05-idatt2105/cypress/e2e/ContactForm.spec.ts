/**
 * E2E Teest, Cypress
 */

describe('Form validation test FAIL', () => {
  beforeEach(() => {
    cy.visit('/contact');
  });

  it('Check if fields display error message correctly', () => {
    // Filling out form with bad info.
    cy.get('[data-testid="nameField"]').type('J');
    cy.get('[data-testid="emailField"]').type('john.com');
    cy.get('[data-testid="messageField"]').type('less10');

    // Trying to submit --> trigger validation
    cy.get('[data-testid="submitButton"]').click();

    // Error messages should appear
    cy.get('[data-testid="nameErrorLabel"]').should('contain', 'Name must be at least 2 characters long.');
    cy.get('[data-testid="emailErrorLabel"]').should('contain', 'Email must be valid.');
    cy.get('[data-testid="messageErrorLabel"]').should('contain', 'Message must be at least 10 characters long.');
  });
});

describe('Form validation test PASS', () => {
  beforeEach(() => {
    cy.visit('/contact');
  });

  it('Submits the form and displays a success message', () => {
    cy.get('[data-testid="nameField"]').type('123 456');
    cy.get('[data-testid="emailField"]').type('123@132.no');
    cy.get('[data-testid="messageField"]').type('This is more than 10 characters.');
    cy.get('[data-testid="submitButton"]').click();

    cy.get('[data-testid=submitMessage]').should('contain', 'Message submitted successfully!');
  });
});


  describe('Button State Test', () => {

    it('Checks if the submit button becomes clickable after form validation', () => {
      cy.visit('/contact');

      cy.get('input[data-testid="nameField"]').type('Jane Doe');
      cy.get('input[data-testid="emailField"]').type('jane@example.com');
      cy.get('textarea[data-testid="messageField"]').type('Hello, this is a test message.');

      cy.get('.button[data-testid="submitButton"]').should('not.be.disabled').click();

    });
  });