import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest, UserResponse } from 'src/models/user.interface';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private urlApi:string = environment.endpoint + "auth/login"
  constructor(private http: HttpClient) { }

  login(request:LoginRequest):Observable<UserResponse>{

    return this.http.post<UserResponse>(`${this.urlApi}`, request)
    
  }
}
