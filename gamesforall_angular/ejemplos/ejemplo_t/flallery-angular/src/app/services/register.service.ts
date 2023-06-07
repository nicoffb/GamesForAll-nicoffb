import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environemnt } from 'src/environments/environment';
import { CreateUserRequest } from '../models/request-dtos/create-user-request.interface';
import { UserResponse } from '../models/response-dtos/create-user-response.interface';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private urlApi:string = environemnt.endpoint + "auth";
  constructor(private http:HttpClient) { }


  registrarUsuario(request:CreateUserRequest):Observable<UserResponse>{

    return this.http.post<UserResponse>(`${this.urlApi}/register`, request)
  }
}
