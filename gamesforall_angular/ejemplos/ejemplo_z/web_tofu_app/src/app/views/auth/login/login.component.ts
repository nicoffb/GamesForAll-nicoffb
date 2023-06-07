import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/models/user.interface';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  formGroup = new FormGroup({
    username: new FormControl("", [Validators.required]),
    password: new FormControl("", [Validators.required, Validators.minLength(8)]),
  })

  usernameErrors: Record<string,string> = {
    required: "The username field can't be empty",
  }
  passwordErrors: Record<string,string> = {
    required: "The password field can't be empty",
    minlength: "The password needs to be atleast 8 characters long"
  }

  constructor(private authService: AuthService, private router: Router){
  }

  login(){
    const formValue = this.formGroup.value
    const userRequest: LoginRequest = {
      username: formValue.username ?? "",
      password: formValue.password ?? ""
    }

    this.authService.login(userRequest).subscribe( res => {
      if(res){
        this.router.navigate(['home'])
      }
    })


  }
}
