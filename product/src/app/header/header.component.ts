import { Component, OnInit } from '@angular/core';
import { MenuService } from '../service/menu.service';
import { AuthService } from '../service/auth.service';
import { Menu } from '../model/menu';
import { LoginResponse } from '../model/user';

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
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.menuService.getData().subscribe((data: any) => {
      this.menus = data;
    });
    
    this.authService.currentUser$.subscribe((user: LoginResponse | null) => {
      this.currentUser = user;
    });
  }
  
  logout(): void {
    this.authService.logout();
  }
}