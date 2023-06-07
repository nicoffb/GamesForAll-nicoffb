import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CheckLoginGuard } from './utils/guards/check-login.guard';
import { CheckNotLoggedGuard } from './utils/guards/check-not-logged.guard';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () =>
      import('./views/home/home.module').then((m) => m.HomeModule),
    canActivate: [CheckLoginGuard],
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./views/auth/login/login.module').then((m) => m.LoginModule),
    canActivate: [CheckNotLoggedGuard],
  },
  {
    path: 'users',
    loadChildren: () =>
      import('./views/users/users.module').then((m) => m.UsersModule),
    canActivate: [CheckLoginGuard],
  },
  {
    path: 'recipes',
    loadChildren: () =>
      import('./views/recipes/recipes.module').then((m) => m.RecipesModule),
    canActivate: [CheckLoginGuard],
  },
  {
    path: 'ingredients',
    loadChildren: () =>
      import('./views/ingredients/ingredients.module').then(
        (m) => m.IngredientsModule
      ),
    canActivate: [CheckLoginGuard],
  },
  {
    path: 'error',
    loadChildren: () =>
      import('./views/error/error.module').then((m) => m.ErrorModule),
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },

  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
