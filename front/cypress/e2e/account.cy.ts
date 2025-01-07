describe('Account details spec', () => {
  it('Show account details', () => {
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

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.intercept('GET', '/api/user/1', {
      statusCode: 200,
      body: {
        id: 1,
        admin: true,
        createdAt: "2024-12-16T15:45:07",
        email: "yoga@studio.com",
        firstName: "Admin",
        lastName: "Admin",
        updatedAt: "2024-12-16T15:45:07"
      },
    }).as('userInfo')

    cy.visit('/login')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.wait('@session')

    cy.get('span.link[routerlink="me"]').click()

    cy.wait('@userInfo')

    cy.get('p').should(($ps) => {
      expect($ps).to.contain('Name: Admin ADMIN')
      expect($ps).to.contain('Email: yoga@studio.com')
      expect($ps).to.contain('You are admin')
      expect($ps).to.contain('Create at:  December 16, 2024')
      expect($ps).to.contain('Last update:  December 16, 2024')
    })
  })

  it('should show button delete on session if user is admin', () => {
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

    cy.intercept('GET', '/api/session', [
      {
        "id": 3,
        "name": "Test",
        "date": "2022-12-12T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Test description",
        "users": [],
        "createdAt": "2025-01-06T13:52:47",
        "updatedAt": "2025-01-06T13:52:47"
      }
    ]).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.intercept('GET', '/api/session/3', {
      statusCode: 200,
      body: {
        "id": 3,
        "name": "Test",
        "date": "2022-12-12T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Test",
        "users": [],
        "createdAt": "2025-01-06T13:52:47",
        "updatedAt": "2025-01-06T13:52:47"
      }
    }).as('getSession')

    cy.intercept('GET', '/api/teacher/1', {
      statusCode: 200,
      body: {
        "id": 1,
        "lastName": "DELAHAYE",
        "firstName": "Margot",
        "createdAt": "2024-12-16T15:45:02",
        "updatedAt": "2024-12-16T15:45:02"
      }
    }).as('getTeacher')

    cy.contains('button', 'Detail').click()

    cy.get('button').should('be.visible').and('contain.text', 'Delete')

  })
})

