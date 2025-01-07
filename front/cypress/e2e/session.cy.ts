describe('Session spec', () => {
  it('should create a session', () => {
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

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.intercept('GET', '/api/teacher', [
        {
          "id": 1,
          "lastName": "DELAHAYE",
          "firstName": "Margot",
          "createdAt": "2024-12-16T15:45:02",
          "updatedAt": "2024-12-16T15:45:02"
        },
        {
          "id": 2,
          "lastName": "THIERCELIN",
          "firstName": "Hélène",
          "createdAt": "2024-12-16T15:45:02",
          "updatedAt": "2024-12-16T15:45:02"
        }
      ]).as('teachers')

    cy.get('button[routerLink="create"]').click()



    cy.get('input[formControlName=name]').type("Test")
    cy.get('input[formControlName=date]').type('2024-12-12')

    cy.get('mat-select[formControlName=teacher_id]').click()
    cy.contains('mat-option', 'Margot DELAHAYE').click()

    cy.get('textarea.mat-input-element[formControlName="description"]').type('Test description')


    cy.intercept('POST', '/api/session', {
      statusCode: 201,
      body: {
        "id": 3,
        "name": "Test",
        "date": "2022-12-12T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Test",
        "users": [],
        "createdAt": "2025-01-06T13:52:46.9813405",
        "updatedAt": "2025-01-06T13:52:46.9813405"
      }
    }).as('createSession')

    cy.intercept('GET', '/api/session', [
      {
        "id": 3,
        "name": "Test",
        "date": "2022-12-12T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Test",
        "users": [],
        "createdAt": "2025-01-06T13:52:47",
        "updatedAt": "2025-01-06T13:52:47"
      }
    ]).as('sessions')

    cy.get('button[type="submit"]').click()

    cy.get('simple-snack-bar').should('be.visible').and('contain.text', 'Session created !')

    cy.url().should('include', '/sessions')

  })

  it('should delete a session', () => {
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
        "description": "Test",
        "users": [],
        "createdAt": "2025-01-06T13:52:47",
        "updatedAt": "2025-01-06T13:52:47"
      }
    ]).as('sessions')

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

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.contains('button', 'Detail').click()

    cy.intercept('DELETE', '/api/session/3', {
      statusCode: 200,
      body: {}
    }).as('deleteSession')

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('updatedSessions')

    cy.get('button').contains('Delete').click()

    cy.get('simple-snack-bar').should('be.visible').and('contain.text', 'Session deleted !')

    cy.url().should('include', '/sessions')

  })

  it('should update a session', () => {
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
        "description": "Test",
        "users": [],
        "createdAt": "2025-01-06T13:52:47",
        "updatedAt": "2025-01-06T13:52:47"
      }
    ]).as('sessions')

    cy.intercept('GET', '/api/session/3', {
      statusCode: 200,
      body: {
        "id": 3,
        "name": "Test",
        "date": "2022-12-12",
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

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.intercept('GET', '/api/teacher', [
      {
        "id": 1,
        "lastName": "DELAHAYE",
        "firstName": "Margot",
        "createdAt": "2024-12-16T15:45:02",
        "updatedAt": "2024-12-16T15:45:02"
      }
    ]).as('getTeacher')

    cy.contains('button', 'Edit').click()

    cy.get('textarea.mat-input-element[formControlName="description"]').type(' update description')

    cy.intercept('PUT', '/api/session/3', {
      statusCode: 200,
      body: {
        date: "2022-12-12",
        description: "Test update description",
        name: "Test",
        teacher_id: 1
      }
    }).as('updateSession')

    cy.intercept('GET', '/api/session', [
      {
        "id": 3,
        "name": "Test",
        "date": "2022-12-12T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Test update description",
        "users": [],
        "createdAt": "2025-01-06T13:52:47",
        "updatedAt": "2025-01-06T13:52:47"
      }
    ]).as('getUpdatedSessions')

    cy.get('button[type="submit"]').click()

    cy.get('simple-snack-bar').should('be.visible').and('contain.text', 'Session updated !')

    cy.url().should('include', '/sessions')

  })

  it('should show the list of sessions', () => {
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
        "description": "Test",
        "users": [],
        "createdAt": "2025-01-06T13:52:47",
        "updatedAt": "2025-01-06T13:52:47"
      }
    ]).as('sessions')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.get('mat-card').should('exist')
    cy.get('mat-card-title').should('contain', 'Test')
    cy.get('mat-card-subtitle').should('contain', 'Session on December 12, 2022')
    cy.get('mat-card-content p').should('contain', 'Test')

  })

  it('should show button Create and Detail on session if user is admin', () => {
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
        "description": "Test",
        "users": [],
        "createdAt": "2025-01-06T13:52:47",
        "updatedAt": "2025-01-06T13:52:47"
      }
    ]).as('sessions')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.contains('button', 'Create').should('be.visible')
    cy.contains('button', 'Detail').should('be.visible')

  })
});
