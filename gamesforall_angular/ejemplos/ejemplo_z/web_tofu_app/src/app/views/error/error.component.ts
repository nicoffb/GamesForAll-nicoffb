import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent {

  error: HttpErrorResponse
  constructor(private router: Router){
    const navigation = this.router.getCurrentNavigation();
    this.error = navigation?.extras?.state?.["error"];
  }
}
