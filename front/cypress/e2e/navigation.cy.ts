describe('Navigation spec', () => {
  it('should redirect to login when not authenticated', () => {
    cy.visit('/sessions')
    cy.url().should('include', '/login')
  })

  it('should show/hide admin features based on user role', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 2,
        username: 'user',
        firstName: 'User',
        lastName: 'Test',
        admin: false
      },
    })

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [{
        id: 1,
        name: "Yoga Session",
        date: "2024-12-12",
        teacher_id: 1,
        description: "Test session",
        users: []
      }]
    }).as('sessions')

    cy.get('input[formControlName=email]').type("user@test.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.get('button[routerLink="create"]').should('not.exist')
  })

  it('should redirect after logout', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      }
    })

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.get('span.link').contains('Logout').click()
    cy.url().should('include', '/')
  })

  it('should handle unauthorized access', () => {
    cy.visit('/sessions/create')
    cy.url().should('include', '/login')
  })

  it('should show correct navigation links based on auth state', () => {
    // Test état non connecté
    cy.visit('/')
    cy.get('span.link').contains('Login').should('be.visible')
    cy.get('span.link').contains('Register').should('be.visible')
    cy.get('span.link').contains('Sessions').should('not.exist')

    // Test état connecté
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'admin',
        firstName: 'Admin',
        lastName: 'Admin',
        admin: true
      }
    })

    cy.visit('/login')
    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type("test!1234")
    cy.get('button[type="submit"]').click()

    cy.get('span.link').contains('Sessions').should('be.visible')
    cy.get('span.link').contains('Account').should('be.visible')
    cy.get('span.link').contains('Logout').should('be.visible')
  })
})
