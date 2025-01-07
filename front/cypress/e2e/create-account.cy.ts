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
});
