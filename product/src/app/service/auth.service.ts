import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { LoginRequest, LoginResponse, SignupRequest } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private currentUserSubject = new BehaviorSubject<LoginResponse | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage(): void {
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
      try {
        const user = JSON.parse(savedUser);
        this.currentUserSubject.next(user);
        console.log('User loaded from storage:', user.username);
      } catch (error) {
        console.error('Error parsing saved user:', error);
        localStorage.removeItem('currentUser');
      }
    }
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap((response: LoginResponse) => {
        this.setCurrentUser(response);
        console.log('Login successful, user saved:', response.username);
      })
    );
  }

  signup(request: SignupRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/signup`, request).pipe(
      tap((response: LoginResponse) => {
        this.setCurrentUser(response);
        console.log('Signup successful, user saved:', response.username);
      })
    );
  }

  private setCurrentUser(user: LoginResponse): void {
    // Save to localStorage
    localStorage.setItem('currentUser', JSON.stringify(user));
    // Update BehaviorSubject
    this.currentUserSubject.next(user);
  }

  logout(): void {
    // Clear localStorage
    localStorage.removeItem('currentUser');
    // Clear BehaviorSubject
    this.currentUserSubject.next(null);
    // Navigate to home
    this.router.navigate(['/']);
    console.log('User logged out');
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null;
  }

  getCurrentUser(): LoginResponse | null {
    return this.currentUserSubject.value;
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser();
    return user?.role === 'ADMIN';
  }

  getUserId(): number | null {
    const user = this.getCurrentUser();
    return user?.id || null;
  }

  getUsername(): string | null {
    const user = this.getCurrentUser();
    return user?.username || null;
  }
}