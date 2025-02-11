describe('Teacher spec', () => {
  it('should show teacher list in create session', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept('GET', '/api/session', []).as('sessions')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.intercept('GET', '/api/teacher', {
      statusCode: 200,
      body: [
        {
          id: 1,
          lastName: "DELAHAYE",
          firstName: "Margot",
          createdAt: "2024-12-16T15:45:02",
          updatedAt: "2024-12-16T15:45:02"
        }
      ]
    }).as('teachers')

    cy.get('button[routerLink="create"]').click()

    cy.get('mat-select[formControlName=teacher_id]').click()
    cy.get('mat-option').should('have.length', 1)
    cy.contains('mat-option', 'Margot DELAHAYE').should('exist')
  })
})
