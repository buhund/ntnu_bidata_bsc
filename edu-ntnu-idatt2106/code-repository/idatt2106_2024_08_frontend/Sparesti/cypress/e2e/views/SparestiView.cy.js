describe('SparestiView', () => {
    beforeEach(() => {
        cy.intercept('GET', '**/api/check_token', {
            statusCode: 200,
            body: { token: 'validtoken' }
        }).as('checkToken')

        cy.intercept('GET', '**/api/goals/user/*', {
            statusCode: 200,
            body: [
                    {
                        "id": 7,
                        "name": "Syden-ferie",
                        "target": 5000,
                        "progress": 5000,
                        "startDate": "2024-04-18",
                        "endDate": "2024-05-01",
                        "completed": true,
                        "challenges": []
                    },
                    {
                        "id": 85,
                        "name": "Ferie",
                        "target": 3453,
                        "progress": 0,
                        "startDate": "2024-05-02",
                        "endDate": "2024-06-01",
                        "completed": false,
                        "challenges": []
                    }
                ]
        }).as('getGoals')

        cy.intercept('GET', '**/api/challenges/goal/7', {
            statusCode: 200,
            body: [
                {
                    "id": 16,
                    "name": "Spar penger på dagligvarer!",
                    "description": "Spar minst 25kr på dagligvarer neste uken.",
                    "challengeType": "SAVING_TARGET",
                    "target": 25,
                    "category": "GROCERIES",
                    "progress": 25,
                    "startDate": "2024-04-24",
                    "endDate": "2024-05-01",
                    "completed": true
                },
                {
                    "id": 21,
                    "name": "Mindre klær!",
                    "description": "Reduser antall innkjøp av klær fra 3 til 2 neste måneden",
                    "challengeType": "REDUCE_SPENDING",
                    "target": 2,
                    "category": "GROCERIES",
                    "progress": 1,
                    "startDate": "2024-04-26",
                    "endDate": "2024-05-03",
                    "completed": false
                },
                {
                    "id": 54,
                    "name": "Spar penger på underholdning!",
                    "description": "Spar minst 246kr på dagligvarer neste uken.",
                    "challengeType": "SAVING_TARGET",
                    "target": 246,
                    "category": "GROCERIES",
                    "progress": 157,
                    "startDate": "2024-05-02",
                    "endDate": "2024-05-09",
                    "completed": false
                }
            ]
        }).as('getChallengesGoal1')

        cy.intercept('GET', '**/api/challenges/goal/85', {
            statusCode: 200,
            body: [
                {
                    "id": 16,
                    "name": "Spar penger på transport!",
                    "description": "Spar minst 25kr på transport neste uken.",
                    "challengeType": "SAVING_TARGET",
                    "target": 25,
                    "category": "GROCERIES",
                    "progress": 25,
                    "startDate": "2024-04-24",
                    "endDate": "2024-05-01",
                    "completed": false
                }
            ]
        }).as('getChallengesGoal2')


        cy.visit('/sparesti')
    });

    it('should show loading text before HTML response', () => {
        cy.get('#loading').should('contain','Laster inn...')
    })

    it('should show goals and challenges', () => {
        cy.wait('@getGoals').then(() => {
            const container = cy.get('.goal-challenge-container')
            container.should('be.visible')
            container.should('contain','Ferie')
            container.should('contain', 'Spar penger på transport')
        })
    })

    it('should able to see detailed goal information', () => {
        cy.wait('@getGoals').then(() => {
            cy.get('.star_with_line').eq(0).click()
            const modalWrapper = cy.get('.modal-wrapper')
            modalWrapper.should('be.visible')
            modalWrapper.should('contain', 'Ferie')
            modalWrapper.should('contain', 'Sparemål:')
            modalWrapper.should('contain', '3453')
        })
    })

    it('should able to close detailed information', () => {
        cy.wait('@getGoals').then(() => {
            cy.get('.star_with_line').eq(0).click()
            const modalWrapper = cy.get('.modal-wrapper')
            modalWrapper.should('be.visible')
            cy.get('#close').click()
            cy.get('.modal-wrapper').should('not.exist')
        })
    })

    it('should able to see detailed challenge information', () => {
        cy.wait('@getGoals').then(() => {
            cy.get('.circle_with_line').eq(1).click()
            const modalWrapper = cy.get('.modal-wrapper')
            modalWrapper.should('be.visible')
            modalWrapper.should('contain', 'Spar penger på transport')
            modalWrapper.should('contain', 'Progresjon:')
            modalWrapper.should('contain', '25 av 25')
        })
    })

    it('should be able to generate a new challenge', () => {
        cy.intercept('GET', '**/api/challenges/generate/goal/*', {
            statusCode: 200,
            body: {
                "id": null,
                "name": "Spar penger på restaurant!",
                "description": "Spar minst 200kr på restaurant neste uken.",
                "challengeType": "SAVING_TARGET",
                "target": 200,
                "category": "RESTAURANT",
                "progress": 0,
                "startDate": "2024-05-03",
                "endDate": "2024-05-10",
                "completed": false
            }
        })

        cy.wait('@getGoals').then(() => {
            cy.get('.circle_with_line').eq(0).click()
            const modalWrapper = cy.get('.modal-wrapper')
            modalWrapper.should('be.visible')
            modalWrapper.should('contain', 'Under ser du en spareutfordring generert kun for deg')
            modalWrapper.should('contain', 'Mål:')
            modalWrapper.should('contain', '200')
        })
    })

    it('should be able to deny a newly generated challenge', () => {
        cy.intercept('GET', '**/api/challenges/generate/goal/*', {
            statusCode: 200,
            body: {
                "id": null,
                "name": "Spar penger på restaurant!",
                "description": "Spar minst 200kr på restaurant neste uken.",
                "challengeType": "SAVING_TARGET",
                "target": 200,
                "category": "RESTAURANT",
                "progress": 0,
                "startDate": "2024-05-03",
                "endDate": "2024-05-10",
                "completed": false
            }
        })

        cy.wait('@getGoals').then(() => {
            cy.get('.circle_with_line').eq(0).click()
            cy.get('.avslaa').click()
            cy.get('.goal-challenge-container').should('not.contain', 'Spar penger på restaurant!')
        })
    })
})