import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';
import { AuthService } from '../../services/auth.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

import { RegisterComponent } from './register.component';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authServiceSpy: jest.Mocked<AuthService>;
  let routerSpy: jest.Mocked<Router>;

  beforeEach(async () => {
    const authSpy = {
      register: jest.fn()
    } as unknown as jest.Mocked<AuthService>;
    const routerMock = {
      navigate: jest.fn()
    } as unknown as jest.Mocked<Router>;
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: AuthService, useValue: authSpy },
        { provide: Router, useValue: routerMock }
      ],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authServiceSpy = TestBed.inject(AuthService) as jest.Mocked<AuthService>;
    routerSpy = TestBed.inject(Router) as jest.Mocked<Router>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('doit s\'inscrire avec des données valides', () => {
    authServiceSpy.register.mockReturnValue(of(void 0));
    component.form.setValue({
      email: 'test@email.com',
      firstName: 'Jean',
      lastName: 'Dupont',
      password: '1234'
    });
    component.submit();
    expect(authServiceSpy.register).toHaveBeenCalledWith({
      email: 'test@email.com',
      firstName: 'Jean',
      lastName: 'Dupont',
      password: '1234'
    });
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
    expect(component.onError).toBeFalsy();
  });

  it('ne doit pas s\'inscrire avec des données invalides', () => {
    authServiceSpy.register.mockReturnValue(throwError(() => new Error('Erreur')));
    component.form.setValue({
      email: 'bad@email.com',
      firstName: 'A',
      lastName: 'B',
      password: '1'
    });
    component.submit();
    expect(authServiceSpy.register).toHaveBeenCalledWith({
      email: 'bad@email.com',
      firstName: 'A',
      lastName: 'B',
      password: '1'
    });
    expect(component.onError).toBeTruthy();
  });
});
