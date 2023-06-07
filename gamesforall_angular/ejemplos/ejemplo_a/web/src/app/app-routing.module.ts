import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/private/dashboard/dashboard.component';
import { LoginComponent } from './pages/public/login/login.component';
import { RestaurantsComponent } from './pages/private/restaurants/restaurants.component';
import { PlatosComponent } from './pages/private/platos/platos.component';
import { RestaurantformComponent } from './pages/private/restaurantform/restaurantform.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login'},
  { path: 'login', component: LoginComponent},
  { path: 'dashboard', component: DashboardComponent},
  { path: 'restaurantes', component: RestaurantsComponent},
  { path: 'platos', component: PlatosComponent},
  { path: 'restaurantform', component: RestaurantformComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
