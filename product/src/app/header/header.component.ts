import { Component, OnInit } from '@angular/core';
import { MenuService } from '../service/menu.service';
import { AuthService } from '../service/auth.service';
import { Menu } from '../model/menu';
import { LoginResponse } from '../model/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit  {
  public menus: Menu[] = [];
  public currentUser: LoginResponse | null = null;

  constructor(
    private menuService: MenuService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.menuService.getData().subscribe((data: any) => {
      this.menus = data.filter((menu: Menu) => 
        menu.routerPath !== 'login' && menu.routerPath !== 'signup'
      );
    });
    
    this.authService.currentUser$.subscribe((user: LoginResponse | null) => {
      this.currentUser = user;
    });
  }

  logout(): void {
    if (confirm('Are you sure you want to logout?')) {
      this.authService.logout();
      this.router.navigate(['/']);
    }
  }

  getUserDisplayName(): string {
    if (!this.currentUser) return '';
    return this.currentUser.firstName || this.currentUser.username;
  }
}