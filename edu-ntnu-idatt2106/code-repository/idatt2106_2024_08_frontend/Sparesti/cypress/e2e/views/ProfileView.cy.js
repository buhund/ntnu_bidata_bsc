describe('ProfileView', () => {
    beforeEach(() => {
        cy.visit('/login');
        cy.get('[name=email]').type('validemail@example.com')
        cy.get('[name=password]').type('validpassword')

        cy.intercept('POST', '**/api/login', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('login')

        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.intercept('GET', '**/api/users/*', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('users')

        cy.visit('/profile');
    });

    it('should log out', () => {

        cy.get('#logOut').click();

        cy.wait('@checkToken').then(() => {
            cy.url().should('include', '/');
        });
    });

    it('should show user information', () => {
        const firstName = 'John';
        const lastName = 'Doe';
        const email = 'johndoe@example.com';
        const password = 'securepassword';
        const knowledgeLevel = 'high';
        const willingnessToHabitChangeLevel = 'hoy';

        cy.intercept('GET', '**/api/users/*', {
            statusCode: 200,
            body: {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                knowledgeLevel: knowledgeLevel,
                willingnessToHabitChangeLevel: willingnessToHabitChangeLevel
            }
        }).as('getUserData');

        cy.wait('@getUserData').then(() => {
            cy.get('input').should('have.length', 6);

            cy.get(' input')
                .eq(0)
                .should('be.visible')
                .should('have.value', 'John');

            cy.get('input')
                .eq(1)
                .should('be.visible')
                .should('have.value', 'Doe');

            cy.get('input')
                .eq(2)
                .should('be.visible')
                .should('have.value', 'johndoe@example.com');

            cy.get('input').eq(3).should('be.visible').should('have.value', 'Høy');
        });
    });

    it('should be able to edit information', () => {
        const firstName = 'John';
        const lastName = 'Doe';
        const email = 'johndoe@example.com';
        const password = 'securepassword';
        const knowledgeLevel = 'high';
        const willingnessToHabitChangeLevel = 'high';

        cy.intercept('GET', '**/api/users/*', {
            statusCode: 200,
            body: {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                knowledgeLevel: knowledgeLevel,
                willingnessToHabitChangeLevel: willingnessToHabitChangeLevel
            }
        }).as('getUserData');

        cy.wait('@getUserData').then(() => {
            cy.intercept('PUT', '**/api/users/*', {
                statusCode: 200,
                body: {
                    firstName: 'Johnny',
                    lastName: lastName,
                    email: email,
                    password: password,
                    knowledgeLevel: knowledgeLevel,
                    willingnessToHabitChangeLevel: willingnessToHabitChangeLevel
                }
            }).as('saveEditedUser')

            cy.get('#edit').click()

            cy.get('input').eq(0).type('ny')

            cy.get('#edit').contains('Lagre').click()

            cy.wait('@saveEditedUser').then(() => {
                cy.get('input').eq(0).should('have.value', 'Johnny').should('be.disabled')
            })
        })
    })

    it('should be able to reach the edit password page', () => {
        const firstName = 'John';
        const lastName = 'Doe';
        const email = 'johndoe@example.com';
        const password = 'securepassword';
        const knowledgeLevel = 'high';
        const willingnessToHabitChangeLevel = 'high';

        cy.intercept('GET', '**/api/users/*', {
            statusCode: 200,
            body: {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                knowledgeLevel: knowledgeLevel,
                willingnessToHabitChangeLevel: willingnessToHabitChangeLevel
            }
        }).as('getUserData');

        cy.get('#edit').click()

        cy.get('#changePassword').click().then(() => {
            cy.url().should('contain','/editPassword')
        })
    }) 
});