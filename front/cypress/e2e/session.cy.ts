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

  it('should handle session participation as normal user', () => {
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

    // Session initiale sans l'utilisateur
    const initialSession = {
      id: 3,
      name: "Yoga Session",
      date: "2024-12-12",
      teacher_id: 1,
      description: "Test session",
      users: [],
      createdAt: "2024-12-16T15:45:02",
      updatedAt: "2024-12-16T15:45:02"
    }

    cy.intercept('GET', '/api/session', [initialSession]).as('sessions')

    cy.intercept('GET', '/api/session/3', initialSession).as('getSession')

    cy.intercept('GET', '/api/teacher/1', {
      statusCode: 200,
      body: {
        id: 1,
        lastName: "DELAHAYE",
        firstName: "Margot",
        createdAt: "2024-12-16T15:45:02",
        updatedAt: "2024-12-16T15:45:02"
      }
    }).as('getTeacher')

    cy.get('input[formControlName=email]').type("user@test.com")
    cy.get('input[formControlName=password]').type("test!1234")
    cy.get('button[type="submit"]').click()

    cy.contains('button', 'Detail').click()

    // Attendre que les données soient chargées
    cy.wait(['@getSession', '@getTeacher'])

    // Vérifier le bouton Participate
    cy.contains('button', 'Participate').should('exist')

    // Intercepter la participation
    cy.intercept('POST', '/api/session/3/participate/2', {
      statusCode: 200
    }).as('participate')

    // Session après participation
    const updatedSession = {
      ...initialSession,
      users: [2]
    }

    cy.intercept('GET', '/api/session/3', updatedSession).as('getUpdatedSession')

    // Cliquer sur Participate
    cy.contains('button', 'Participate').click()

    // Attendre la mise à jour
    cy.wait('@participate')
    cy.wait('@getUpdatedSession')

    // Vérifier le nouveau bouton
    cy.contains('button', 'Do not participate').should('exist')
  })

  it('should handle empty sessions list', () => {
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

    cy.get('mat-card-title').should('contain.text', 'Rentals available')

    cy.get('mat-card.item').should('not.exist')
    cy.get('button').contains('Detail').should('not.exist')
    cy.get('mat-card-subtitle').should('not.exist')
  })

  it('should handle session detail view', () => {
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

    cy.intercept('GET', '/api/session', [{
      id: 3,
      name: "Test Session",
      date: "2024-12-12",
      teacher_id: 1,
      description: "Test description",
      users: [1, 2]
    }]).as('sessions')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.intercept('GET', '/api/teacher/1', {
      statusCode: 200,
      body: {
        id: 1,
        lastName: "DELAHAYE",
        firstName: "Margot",
        createdAt: "2024-12-16T15:45:02",
        updatedAt: "2024-12-16T15:45:02"
      }
    }).as('getTeacher')

    cy.intercept('GET', '/api/session/3', {
      id: 3,
      name: "Test Session",
      date: "2024-12-12",
      teacher_id: 1,
      description: "Test description",
      users: [1, 2]
    }).as('getSession')

    cy.contains('button', 'Detail').click()

    cy.get('mat-card-title').should('contain', 'Test Session')
    cy.get('mat-card-subtitle').should('contain', 'Margot DELAHAYE')
    cy.get('mat-card-content').should('contain', 'Test description')
  })

  it('should validate form fields and show errors', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'admin',
        firstName: 'Admin',
        lastName: 'Admin',
        admin: true
      }
    })

    cy.intercept('GET', '/api/session', []).as('sessions')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type("test!1234")
    cy.get('button[type="submit"]').click()

    cy.intercept('GET', '/api/teacher', [{
      id: 1,
      lastName: "DELAHAYE",
      firstName: "Margot",
      createdAt: "2024-12-16T15:45:02",
      updatedAt: "2024-12-16T15:45:02"
    }]).as('teachers')

    cy.get('button[routerLink="create"]').click()

    cy.get('button[type="submit"]').should('be.disabled')

    cy.get('input[formControlName=name]').type('Test').clear()
    cy.get('mat-form-field').first().should('contain', 'Name *')

    cy.get('textarea[formControlName=description]').type('Test').clear()
    cy.get('mat-form-field').last().should('contain', 'Description *')
  })

  it('should show complete session details', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'admin',
        firstName: 'Admin',
        lastName: 'Admin',
        admin: true
      }
    })

    const mockSession = {
      id: 3,
      name: "Test Session",
      date: "2024-12-12",
      teacher_id: 1,
      description: "Test description",
      users: [1, 2, 3],
      createdAt: "2024-01-01T10:00:00",
      updatedAt: "2024-01-02T15:30:00"
    }

    cy.intercept('GET', '/api/session', [mockSession]).as('sessions')

    cy.intercept('GET', '/api/session/3', mockSession).as('getSession')

    cy.intercept('GET', '/api/teacher/1', {
      statusCode: 200,
      body: {
        id: 1,
        lastName: "DELAHAYE",
        firstName: "Margot",
        createdAt: "2024-12-16T15:45:02",
        updatedAt: "2024-12-16T15:45:02"
      }
    }).as('getTeacher')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type("test!1234")
    cy.get('button[type="submit"]').click()

    cy.contains('button', 'Detail').click()

    cy.wait(['@getSession', '@getTeacher'])

    cy.get('h1').should('contain', mockSession.name)
    cy.get('mat-card-subtitle').should('contain', 'Margot DELAHAYE')
    cy.get('mat-icon').contains('group')
    cy.get('span').contains('3 attendees')
    cy.get('.description').should('contain', mockSession.description)
    cy.get('.created').should('contain', 'January 1, 2024')
    cy.get('.updated').should('contain', 'January 2, 2024')
  })

  it('should show edit button for admin in session detail', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'admin',
        firstName: 'Admin',
        lastName: 'Admin',
        admin: true
      }
    })

    const session = {
      id: 3,
      name: "Test Session",
      date: "2024-12-12",
      teacher_id: 1,
      description: "Test description",
      users: [1, 2],
      createdAt: "2024-01-01T10:00:00",
      updatedAt: "2024-01-02T15:30:00"
    }

    cy.intercept('GET', '/api/session', [session]).as('sessions')
    cy.intercept('GET', '/api/session/3', session).as('getSession')
    cy.intercept('GET', '/api/teacher/1', {
      id: 1,
      lastName: "DELAHAYE",
      firstName: "Margot",
      createdAt: "2024-12-16T15:45:02",
      updatedAt: "2024-12-16T15:45:02"
    }).as('getTeacher')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type("test!1234")
    cy.get('button[type="submit"]').click()

    cy.contains('button', 'Detail').click()

    cy.wait(['@getSession', '@getTeacher'])

    // Vérifier les boutons d'admin
    cy.get('button').contains('Edit').should('exist')
    cy.get('button').contains('Delete').should('exist')

    // Vérifier les détails de la session
    cy.get('.my2').first().within(() => {
      cy.get('mat-icon').contains('group')
      cy.get('span').contains('2 attendees')
    })

    cy.get('.my2').last().within(() => {
      cy.get('.created').should('contain', 'January 1, 2024')
      cy.get('.updated').should('contain', 'January 2, 2024')
    })
  })

  it('should handle session update with invalid data', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'admin',
        firstName: 'Admin',
        lastName: 'Admin',
        admin: true
      }
    })

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type("test!1234")
    cy.get('button[type="submit"]').click()

    const session = {
      id: 3,
      name: "Test Session",
      date: "2024-12-12",
      teacher_id: 1,
      description: "Test description",
      users: [],
      createdAt: "2024-01-01T10:00:00",
      updatedAt: "2024-01-02T15:30:00"
    }

    cy.intercept('GET', '/api/session', [session]).as('sessions')
    cy.intercept('GET', '/api/session/3', session).as('getSession')

    cy.contains('button', 'Detail').click()
    cy.contains('button', 'Edit').click()

    // Test validation des champs
    cy.get('input[formControlName=name]').clear()
    cy.get('button[type="submit"]').should('be.disabled')

    cy.get('input[formControlName=date]').clear()
    cy.get('button[type="submit"]').should('be.disabled')

    cy.get('textarea[formControlName=description]').clear()
    cy.get('button[type="submit"]').should('be.disabled')
  })
});
