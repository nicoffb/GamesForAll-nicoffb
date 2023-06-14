import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { UserResponse } from 'src/app/models/user.interface';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor(private _snackBar:MatSnackBar,private http:HttpClient) { }

 mostrarAlerta(mensaje:string, tipo:string){
  this._snackBar.open(mensaje,tipo,{
    horizontalPosition:"end",
    verticalPosition:"top",
    duration:3000
  })
 }


 guardarSesionUsuario(userResponse: UserResponse){
  localStorage.setItem("user", userResponse.token);
 }


 obtenerSesionUsuario(){
  const userData = localStorage.getItem("user");
  const user = JSON.parse(userData!);
  return user;
 }

 eliminarSesionUsuario(){
  localStorage.removeItem("user");
 }

 
 getLoggedUser(): Observable<UserResponse> {
  return this.http.get<UserResponse>(`http://localhost:8080/me`);
}
}
