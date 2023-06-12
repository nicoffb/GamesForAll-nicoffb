import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/models/user.interface';
import { LoginService } from 'src/app/services/login.service';
import { UtilService } from 'src/app/services/util.service';




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

    this._loginService.login(request).subscribe(resp=>{
      localStorage.setItem('token',resp.token);
      this.router.navigate(['user'])
    })

  }

}
