import { TestBed } from '@angular/core/testing';
import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize with isLogged as false', () => {
    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

  describe('logIn', () => {
    it('should set session information and isLogged to true', () => {
      const mockUser: SessionInformation = {
        token: 'fake-token',
        type: 'Bearer',
        id: 1,
        username: 'testuser',
        firstName: 'Test',
        lastName: 'User',
        admin: false
      };

      service.logIn(mockUser);

      expect(service.isLogged).toBe(true);
      expect(service.sessionInformation).toEqual(mockUser);
    });

    it('should emit true in isLogged$ observable when logging in', (done) => {
      const mockUser: SessionInformation = {
        token: 'fake-token',
        type: 'Bearer',
        id: 1,
        username: 'testuser',
        firstName: 'Test',
        lastName: 'User',
        admin: false
      };

      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toBeTruthy();
        done();
      });

      service.logIn(mockUser);
    });
  });

  describe('logOut', () => {
    it('should clear session information and set isLogged to false', () => {
      const mockUser: SessionInformation = {
        token: 'fake-token',
        type: 'Bearer',
        id: 1,
        username: 'testuser',
        firstName: 'Test',
        lastName: 'User',
        admin: false
      };
      service.logIn(mockUser);

      service.logOut();

      expect(service.isLogged).toBe(false);
      expect(service.sessionInformation).toBeUndefined();
    });

    it('should emit false in isLogged$ observable when logging out', (done) => {
      const mockUser: SessionInformation = {
        token: 'fake-token',
        type: 'Bearer',
        id: 1,
        username: 'testuser',
        firstName: 'Test',
        lastName: 'User',
        admin: false
      };
      service.logIn(mockUser);

      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toBeFalsy();
        done();
      });

      service.logOut();
    });
  });

  describe('$isLogged', () => {
    it('should return an Observable of the current logged status', (done) => {
      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toBeFalsy();
        done();
      });
    });

    it('should emit new values when login status changes', (done) => {
      const mockUser: SessionInformation = {
        token: 'fake-token',
        type: 'Bearer',
        id: 1,
        username: 'testuser',
        firstName: 'Test',
        lastName: 'User',
        admin: false
      };

      const emittedValues: boolean[] = [];

      service.$isLogged().subscribe(isLogged => {
        emittedValues.push(isLogged);
        if (emittedValues.length === 3) {
          expect(emittedValues).toEqual([false, true, false]);
          done();
        }
      });

      service.logIn(mockUser);
      service.logOut();
    });
  });
});
