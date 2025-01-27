import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from 'src/app/services/teacher.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';

import { FormComponent } from './form.component';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let sessionApiService: jest.Mocked<SessionApiService>;
  let router: Router;
  let sessionService: SessionService;

  const mockSession = {
    id: '1',
    name: 'Test Session',
    date: '2024-01-01',
    teacher_id: 1,
    description: 'Test Description',
    users: [],
    createdAt: new Date(),
    updatedAt: new Date()
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FormComponent],
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        MatSnackBarModule,
        HttpClientModule
      ],
      providers: [
        {
          provide: SessionService,
          useValue: { sessionInformation: { admin: true } }
        },
        {
          provide: SessionApiService,
          useValue: {
            create: jest.fn().mockReturnValue(of(mockSession)),
            update: jest.fn().mockReturnValue(of(mockSession)),
            detail: jest.fn().mockReturnValue(of(mockSession))
          }
        },
        {
          provide: TeacherService,
          useValue: { all: jest.fn().mockReturnValue(of([])) }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    sessionApiService = TestBed.inject(SessionApiService) as jest.Mocked<SessionApiService>;
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should initialize form for creation', () => {
    component.ngOnInit();
    expect(component.onUpdate).toBeFalsy();
    expect(component.sessionForm).toBeTruthy();
  });

  it('should initialize form for update', () => {
    const routerSpy = jest.spyOn(router, 'url', 'get').mockReturnValue('/sessions/update/1');
    component.ngOnInit();
    expect(component.onUpdate).toBeTruthy();
    expect(sessionApiService.detail).toHaveBeenCalled();
  });

  it('should submit form for creation', () => {
    component.ngOnInit();
    component.sessionForm?.patchValue({
      name: 'Test Session',
      date: '2024-01-01',
      teacher_id: 1,
      description: 'Test Description'
    });
    component.submit();
    expect(sessionApiService.create).toHaveBeenCalled();
  });

  it('should submit form for update', () => {
    const routerSpy = jest.spyOn(router, 'url', 'get').mockReturnValue('/sessions/update/1');
    component.ngOnInit();
    component.sessionForm?.patchValue({
      name: 'Updated Session',
      date: '2024-01-01',
      teacher_id: 1,
      description: 'Updated Description'
    });
    component.submit();
    expect(sessionApiService.update).toHaveBeenCalled();
  });

  it('should validate required fields', () => {
    component.ngOnInit();
    expect(component.sessionForm?.get('name')?.errors?.['required']).toBeTruthy();
    expect(component.sessionForm?.get('date')?.errors?.['required']).toBeTruthy();
    expect(component.sessionForm?.get('teacher_id')?.errors?.['required']).toBeTruthy();
    expect(component.sessionForm?.get('description')?.errors?.['required']).toBeTruthy();
  });
});
