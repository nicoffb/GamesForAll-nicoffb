import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginModule } from './views/auth/login/login.module';

import { SharedModule } from './modules/shared/shared.module';
import { HttpErrorInterceptor } from './services/interceptors/http-error.interceptor';
import { AutofocusDirective } from './directives/autofocus.directive';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { AuthInterceptor } from './services/interceptors/auth.interceptor';
import { MaterialModule } from './modules/material.module';
import { LoadingInterceptor } from './services/interceptors/loading.interceptor';

@NgModule({
  declarations: [AppComponent, AutofocusDirective],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LoginModule,
    SharedModule,
    HttpClientModule,
    InfiniteScrollModule,
    MaterialModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true,
    },

    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
