import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { LoginRequest, LoginResponse } from '../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  credentials: LoginRequest = {
    username: '',
    password: ''
  };
  
  errorMessage: string = '';
  isLoading: boolean = false;
  returnUrl: string = '/';

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    
    // If already logged in, redirect
    if (this.authService.isLoggedIn()) {
      this.router.navigate([this.returnUrl]);
    }
  }

  onSubmit(): void {
    this.errorMessage = '';
    this.isLoading = true;

    this.authService.login(this.credentials).subscribe({
      next: (response: LoginResponse) => {
        console.log('Login successful:', response);
        this.router.navigate([this.returnUrl]);
      },
      error: (error: any) => {
        this.errorMessage = error.error?.message || 'Login failed. Please try again.';
        this.isLoading = false;
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}