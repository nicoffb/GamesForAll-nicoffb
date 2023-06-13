import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from 'src/app/components/login/login.component';
import { UserComponent } from './components/user/user.component';
import { CategoriesComponent } from './components/categories/categories.component';
import { PlatformsComponent } from './components/platforms/platforms.component';

const routes: Routes = [
  { path:'',redirectTo:'login',pathMatch:'full'},
  { path: 'login', component: LoginComponent },
  { path: 'user', component: UserComponent },
  { path: 'categories', component: CategoriesComponent },
  { path: 'platforms', component: PlatformsComponent },
  {path:'**',redirectTo:'login',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
