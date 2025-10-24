import { Component, OnInit } from '@angular/core';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'BautistaCorp';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    console.log('App initialized, checking for saved session...');
  }
}