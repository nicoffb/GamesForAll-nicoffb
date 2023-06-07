import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/public/login/login.component';
import { DashboardComponent } from './pages/private/dashboard/dashboard.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { FooterComponent } from './pages/layout/footer/footer.component';
import { SidenavComponent } from './pages/layout/sidenav/sidenav.component';
import { HttpinterceptorService } from './core/services/httpinterceptor.service';
import { RestaurantsComponent } from './pages/private/restaurants/restaurants.component';
import { PlatosComponent } from './pages/private/platos/platos.component';
import { RestaurantformComponent } from './pages/private/restaurantform/restaurantform.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    FooterComponent,
    SidenavComponent,
    RestaurantsComponent,
    PlatosComponent,
    RestaurantformComponent
  ], 
  imports: [
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpinterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
