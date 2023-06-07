import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/models/request-dtos/login-request.interface';
import { UtilService } from 'src/app/reutilizable/util.service';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {


  formularioLogin: FormGroup;
  ocultarPassword: boolean = true;
  mostrarLoading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private _loginService: LoginService,
    private _utilService: UtilService
  ) {
    this.formularioLogin = this.fb.group({
      username: ["", Validators.required],
      password: ["", Validators.required]
    })
  }

  iniciarSesion() {

    this.mostrarLoading = true;

    const request: LoginRequest = {
      username: this.formularioLogin.value.username,
      password: this.formularioLogin.value.password
    }

    this._loginService.login(request).subscribe({
      next: (data) => {
        if (data) {
          this._utilService.guardarSesionUsuario(data)
          this.router.navigate(["pages/dashboard"])
          localStorage.setItem("auth_token", data.token)
        } else {
          this._utilService.mostrarAlerta("No se encontraron coincidencias", "Oops")
        }
      },
      complete:()=>{
        this.mostrarLoading = false;
      },
      error: () =>{
        this._utilService.mostrarAlerta("Hubo un error", "Oops")
      }
    })

  }

}
