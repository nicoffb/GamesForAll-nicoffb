import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environemnt } from 'src/environments/environment';
import { LoginRequest } from '../models/request-dtos/login-request.interface';
import { JwtUserResponse } from '../models/response-dtos/login-response.interface';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private urlApi:string = environemnt.endpoint + "auth/login"
  constructor(private http: HttpClient) { }

  login(request:LoginRequest):Observable<JwtUserResponse>{

    return this.http.post<JwtUserResponse>(`${this.urlApi}`, request)
    
  }
}
