describe('Create session spec', () => {
  it('should send an input error', () => {
    cy.visit('/register')

    cy.get('input[formControlName=firstName]').type("Toto")
    cy.get('input[formControlName=lastName]').type("Toto")
    cy.get('input[formControlName=lastName]').should('have.class', 'ng-valid')

    cy.get('input[formControlName=lastName]').clear()

    cy.get('input[formControlName=lastName]').should('have.class', 'ng-invalid')
  })

  it('should create an account', () => {
    cy.visit('/register')

    cy.intercept('POST', '/api/auth/register', {
      body: {
        firstName: 'Toto',
        lastName: 'Tata',
        email: 'test@test.com',
        password: 'test1234'
      },
    })

    cy.get('input[formControlName=firstName]').type("Toto")
    cy.get('input[formControlName=lastName]').type("Tata")
    cy.get('input[formControlName=email]').type("test@test.com")
    cy.get('input[formControlName=password]').type(`${"test1234"}{enter}{enter}`)

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 2,
        username: 'Test',
        firstName: 'Toto',
        lastName: 'Tata',
        admin: false
      },
    })

    cy.get('input[formControlName=email]').type("test@test.com")
    cy.get('input[formControlName=password]').type(`${"test1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
  })

  it('should handle registration errors', () => {
    cy.visit('/register')

    cy.intercept('POST', '/api/auth/register', {
      statusCode: 400,
      body: {
        message: 'Email already exists'
      }
    }).as('registerError')

    cy.get('input[formControlName=firstName]').type("Test")
    cy.get('input[formControlName=lastName]').type("User")
    cy.get('input[formControlName=email]').type("existing@email.com")
    cy.get('input[formControlName=password]').type("test1234")

    cy.get('button[type="submit"]').click()

    cy.get('span.error.ml2').should('exist')
      .and('contain.text', 'An error occurred')
  })

  it('should validate all form fields', () => {
    cy.visit('/register')

    // Test champs vides
    cy.get('button[type="submit"]').should('be.disabled')

    // Test validation email
    cy.get('input[formControlName=email]').type('invalid-email')
    cy.get('mat-error').should('exist')

    // Test validation mot de passe
    cy.get('input[formControlName=password]').type('short')
    cy.get('mat-error').should('exist')

    // Test validation prénom
    cy.get('input[formControlName=firstName]').type('A').clear()
    cy.get('mat-error').should('exist')

    // Test validation nom
    cy.get('input[formControlName=lastName]').type('B').clear()
    cy.get('mat-error').should('exist')
  })
});
