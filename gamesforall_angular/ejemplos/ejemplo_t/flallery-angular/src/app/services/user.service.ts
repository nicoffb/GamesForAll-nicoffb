import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environemnt } from 'src/environments/environment';
import { CreateUserRequest } from '../models/request-dtos/create-user-request.interface';
import { UserResponse } from '../models/response-dtos/create-user-response.interface';
import { UserList } from '../models/response-dtos/create-user-response.interface';
import { EditUserRequest } from '../models/request-dtos/edit-user-request.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private urlApi:string = environemnt.endpoint + "auth";

  constructor(private http:HttpClient) { }


  registrarUsuario(request:CreateUserRequest):Observable<UserResponse>{

    return this.http.post<UserResponse>(`${this.urlApi}/register`, request)
  }


  lista(): Observable<UserList>{
    return this.http.get<UserList>(`${this.urlApi}/user/`)
  }


  guardar(request: CreateUserRequest): Observable<UserResponse>{
    return this.http.post<UserResponse>(`${this.urlApi}/register`,request)
  }


  editar(request: EditUserRequest): Observable<UserResponse>{
    return this.http.put<UserResponse>(`${this.urlApi}/editUser`,request)
  }


  eliminar(uuid:string): Observable<UserResponse>{
    return this.http.delete<UserResponse>(`${this.urlApi}/user/${uuid}`)
  }
  cambiarEstadoBaneoUsuario(id: string): Observable<UserResponse>{
    return this.http.put<UserResponse>(`http://localhost:8080/user/${id}/changeStatus`, null);
  }

  cambiarRol(id:string): Observable<UserResponse>{
    return this.http.put<UserResponse>(`http://localhost:8080/user/${id}/role/`, null);
  }
}

