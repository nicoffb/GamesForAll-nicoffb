import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserResponse } from 'src/models/user.interface';


@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor(private _snackBar:MatSnackBar) { }

 mostrarAlerta(mensaje:string, tipo:string){
  this._snackBar.open(mensaje,tipo,{
    horizontalPosition:"end",
    verticalPosition:"top",
    duration:3000
  })
 }


 guardarSesionUsuario(userResponse: UserResponse){
  localStorage.setItem("user",JSON.stringify(userResponse));
 }


 obtenerSesionUsuario(){
  const userData = localStorage.getItem("user");
  const user = JSON.parse(userData!);
  return user;
 }

 eliminarSesionUsuario(){
  localStorage.removeItem("user");
 }
}
