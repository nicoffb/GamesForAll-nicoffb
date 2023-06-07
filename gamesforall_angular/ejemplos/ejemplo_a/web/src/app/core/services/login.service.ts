import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest } from '../model/loginRequest';
import { LoginResponse } from '../model/loginResponse';
import { Router } from '@angular/router';



@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, @Inject(Router) private router: Router) { }

  public logIn(loginRequest: LoginRequest): Observable<LoginResponse>{
    return this.http.post<LoginResponse>(`${environment.URL_BASE_API}/auth/login`, loginRequest);
  }

  public logout() {
    localStorage.removeItem("token");
    this.router.navigate(['login']);
  }

}
