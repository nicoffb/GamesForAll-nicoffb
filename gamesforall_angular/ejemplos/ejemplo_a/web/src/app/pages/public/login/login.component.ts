import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { LoginService } from 'src/app/core/services/login.service';
import { LoginRequest } from 'src/app/core/model/loginRequest';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    usuario: new FormControl(),
    pass: new FormControl()
  })

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit() {
    let request : LoginRequest = {
      username: this.loginForm.value.usuario,
      password: this.loginForm.value.pass
    }
    debugger;
    this.loginService.logIn(request).subscribe(response => {
      debugger;
      localStorage.setItem("token", response.token);
      this.router.navigate(['dashboard']);
    });
  }


}
