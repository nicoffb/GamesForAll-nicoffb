import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  OnInit,
} from '@angular/core';
import { AuthService } from './services/auth.service';
import { LoadingService } from './services/loading.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  isLoggedIn = false;
  loading = false;

  constructor(
    private authService: AuthService,
    private loadingService: LoadingService,
    private changeDetector: ChangeDetectorRef,
    private router : Router,
  ) {
    this.authService.checkToken()
  }

  ngOnInit(): void {
    this.loadingService.getLoader().subscribe((res) => {
      this.loading = res;
    });
    this.authService.isLogged.subscribe((isLogged) => {
      this.isLoggedIn = isLogged;
    });
  }

  ngAfterContentChecked(): void {
    this.changeDetector.detectChanges();
  }

  checkHome(): boolean{
    return this.router.url === '/home'
  }

  title = 'web_tofu_app';
}
