import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  @ViewChild('button') button: unknown;
  constructor(private authService: AuthService, private router: Router) {}
  activeButton= "btnHome"
  logout() {
    this.authService.logout();
  }
  
  setActive(btnName:string){
    this.activeButton = btnName
  }
  isActive(btnName: string){
    return this.activeButton === btnName
  }

  navigateHome(){
    this.setActive('btnHome')
    this.router.navigate(['home'])

  }
  navigateUsers(){
    this.setActive('btnUsers')
    this.router.navigate(['users'])

  }
  navigateRecipes(){
    this.setActive('btnRecipes')
    this.router.navigate(['recipes'])

  }
  navigateIngredient(){
    this.setActive('btnIngredients')
    this.router.navigate(['ingredients'])

  }
  navigateError(){
    this.setActive('btnError')
    this.router.navigate(['error'])

  }
}
