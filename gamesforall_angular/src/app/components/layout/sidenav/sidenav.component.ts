import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
menuItems: any;

  constructor(
    private loginService: LoginService,
    private router: Router
  ) { } 

  ngOnInit(): void {
  }

  
  doLogOut(){
    this.loginService.logOut();
    this.router.navigate(['admin-login'])
    }

}
