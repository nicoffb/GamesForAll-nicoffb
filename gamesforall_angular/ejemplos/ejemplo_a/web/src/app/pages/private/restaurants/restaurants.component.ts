import { Component, OnInit } from '@angular/core';
import { RestauranteResponse } from 'src/app/core/model/restauranteResponse';
import { RestauranteService } from 'src/app/core/services/restaurante.service';

@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.component.html',
  styleUrls: ['./restaurants.component.css']
})
export class RestaurantsComponent implements OnInit {

  restaurantes: RestauranteResponse[] = [];
  

  constructor(private restauranteService: RestauranteService) { }

  ngOnInit(): void {
    this.restauranteService.getManaged().subscribe(response => {
      this.restaurantes = response.contenido;
    })
  }

}
