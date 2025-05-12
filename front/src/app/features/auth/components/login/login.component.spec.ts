import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceSpy: jest.Mocked<AuthService>;
  let routerSpy: jest.Mocked<Router>;
  let sessionService: SessionService;

  beforeEach(async () => {
    const authSpy = {
      login: jest.fn()
    } as unknown as jest.Mocked<AuthService>;
    const routerMock = {
      navigate: jest.fn()
    } as unknown as jest.Mocked<Router>;
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        SessionService,
        { provide: AuthService, useValue: authSpy },
        { provide: Router, useValue: routerMock }
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authServiceSpy = TestBed.inject(AuthService) as jest.Mocked<AuthService>;
    routerSpy = TestBed.inject(Router) as jest.Mocked<Router>;
    sessionService = TestBed.inject(SessionService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('doit se connecter avec des identifiants valides', () => {
    const sessionMock: SessionInformation = {
      token: 'abc',
      type: 'Bearer',
      id: 1,
      username: 'test',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };
    const logInSpy = jest.spyOn(sessionService, 'logIn');
    authServiceSpy.login.mockReturnValue(of(sessionMock));
    component.form.setValue({ email: 'test@email.com', password: '1234' });
    component.submit();
    expect(authServiceSpy.login).toHaveBeenCalledWith({ email: 'test@email.com', password: '1234' });
    expect(logInSpy).toHaveBeenCalledWith(sessionMock);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/sessions']);
    expect(component.onError).toBeFalsy();
  });

  it('ne doit pas se connecter avec de mauvais identifiants', () => {
    authServiceSpy.login.mockReturnValue(throwError(() => new Error('Unauthorized')));
    component.form.setValue({ email: 'wrong@email.com', password: 'wrong' });
    component.submit();
    expect(authServiceSpy.login).toHaveBeenCalledWith({ email: 'wrong@email.com', password: 'wrong' });
    expect(component.onError).toBeTruthy();
  });
});
