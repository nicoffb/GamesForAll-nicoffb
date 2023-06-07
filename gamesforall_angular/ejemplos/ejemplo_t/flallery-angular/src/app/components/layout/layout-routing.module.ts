import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArtworkComponent } from './pages/artwork/artwork.component';
import { UserComponent } from './pages/user/user.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { HistorialVentaComponent } from './pages/historial-venta/historial-venta.component';
import { LayoutComponent } from './layout.component';
import { ArtworkCategoriasComponent } from 'src/app/components/layout/pages/artwork-categorias/artwork-categorias.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent,
    children: [
      {path:'dashboard',component:DashboardComponent},
      {path:'users',component:UserComponent},
      {path:'artwork',component:ArtworkComponent},
      {path:'categorias',component:ArtworkCategoriasComponent},
      {path:'historial_ventas',component:HistorialVentaComponent}
    ]
  }
  ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule { }
