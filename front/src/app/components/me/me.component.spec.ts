import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/services/user.service';
import { MeComponent } from './me.component';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userService: UserService;
  let sessionService: SessionService;
  let router: Router;

  const mockUser = {
    id: 1,
    email: 'test@test.com',
    lastName: 'Test',
    firstName: 'User',
    admin: false,
    password: 'password',
    createdAt: new Date(),
    updatedAt: new Date()
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [MatSnackBarModule],
      providers: [
        {
          provide: SessionService,
          useValue: {
            sessionInformation: { id: '1', admin: false },
            logOut: jest.fn()
          }
        },
        {
          provide: UserService,
          useValue: {
            getById: jest.fn().mockReturnValue(of(mockUser)),
            delete: jest.fn().mockReturnValue(of({}))
          }
        },
        {
          provide: Router,
          useValue: { navigate: jest.fn() }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load user data on init', () => {
    const spy = jest.spyOn(userService, 'getById');
    component.ngOnInit();
    expect(spy).toHaveBeenCalledWith('1');
    expect(component.user).toEqual(mockUser);
  });

  it('should navigate back when back() is called', () => {
    const historySpy = jest.spyOn(window.history, 'back');
    component.back();
    expect(historySpy).toHaveBeenCalled();
  });

  it('should delete user account', () => {
    const routerSpy = jest.spyOn(router, 'navigate');
    const deleteSpy = jest.spyOn(userService, 'delete');
    const logoutSpy = jest.spyOn(sessionService, 'logOut');

    component.delete();

    expect(deleteSpy).toHaveBeenCalledWith('1');
    expect(logoutSpy).toHaveBeenCalled();
    expect(routerSpy).toHaveBeenCalledWith(['/']);
  });

  it('should display admin status when user is admin', () => {
    component.user = { ...mockUser, admin: true };
    fixture.detectChanges();
    const adminElement = fixture.nativeElement.querySelector('p.my2');
    expect(adminElement?.textContent).toContain('You are admin');
  });

  it('should show delete button only for non-admin users', () => {
    component.user = mockUser;
    fixture.detectChanges();
    const deleteButton = fixture.nativeElement.querySelector('button[color="warn"]');
    expect(deleteButton).toBeTruthy();
  });
});
