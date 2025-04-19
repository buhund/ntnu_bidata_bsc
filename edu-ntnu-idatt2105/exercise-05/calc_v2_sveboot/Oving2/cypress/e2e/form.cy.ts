describe('form spec', () => {
  it('DefaultCaseCheck', () => {
    cy.visit('http://localhost:4173/Bugs')
    cy.get('[data-testid="name"]').should('be.empty')
    cy.get('[data-testid="email"]').should('be.empty')
    cy.get('[data-testid="description"]').should('be.empty')
    cy.get('[data-testid="submit"]').should('be.disabled')
  })

  it('WrongInputCheck', () =>{
    cy.visit('http://localhost:4173/Bugs')
    cy.get('[data-testid="name"]').type("TestName")
    cy.get('[data-testid="email"]').type("ttttt")
    cy.get('[data-testid="description"]').type("Test")
    cy.get('[data-testid="submit"]').should('be.disabled')
  })

  it('CorrectInputCheck', () =>{
    cy.visit('http://localhost:4173/Bugs')
    cy.get('[data-testid="name"]').type("TestName")
    cy.get('[data-testid="email"]').type("Test@test.test")
    cy.get('[data-testid="description"]').type("Test")
    cy.get('[data-testid="submit"]').should('be.enabled')
  })

  it('FormSubmissionCheck', () =>{
    cy.visit('http://localhost:4173/Bugs')

    cy.window().then((win) => {
      cy.stub(win, 'alert');
    });

    cy.get('[data-testid="name"]').type("TestName")
    cy.get('[data-testid="email"]').type("Test@test.test")
    cy.get('[data-testid="description"]').type("Test")
    cy.get('[data-testid="submit"]').click()

    cy.wait(2000);

    cy.window().then((win) => {
      expect(win.alert).to.be.calledWith('Form submitted successfully!'); // Ensure this matches exactly the alert message in your application
    });
  })
})