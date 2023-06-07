import { Component, OnInit } from '@angular/core';
import { PlatoResponse } from 'src/app/core/model/plato';
import { RestauranteResponse } from 'src/app/core/model/restauranteResponse';
import { PlatoService } from 'src/app/core/services/plato.service';
import { RestauranteService } from 'src/app/core/services/restaurante.service';

@Component({
  selector: 'app-platos',
  templateUrl: './platos.component.html',
  styleUrls: ['./platos.component.css']
})
export class PlatosComponent implements OnInit {



  restaurantes: RestauranteResponse[] = [];
  platos: PlatoResponse[] = [];
  restauranteSelected: RestauranteResponse | undefined;
  platoEliminar: PlatoResponse | undefined;
  eliminado: boolean = false;

  constructor(private platoService: PlatoService, private restaurantservice: RestauranteService) { }

  ngOnInit(): void {
    this.restaurantservice.getManaged().subscribe(res => {
      this.restaurantes = res.contenido;
      this.restauranteSelected = this.restaurantes[0];
      this.platoService.getFromRestaurante(this.restauranteSelected.id, 0, 15).subscribe(platos => this.platos = platos.contenido);
    })
  }

  selectPlatoToDelete(plato: PlatoResponse) {
    this.platoEliminar = plato;
  }

  eliminar() {
    this.platoService.delete(this.platoEliminar!.id).subscribe(
      (res) => this.platos = this.platos.filter(p => p.id != this.platoEliminar!.id),
      (err) => console.error("No se ha podido borrar el plato"),
      () => console.log("observable complete")
      
    );
    
  }

}
