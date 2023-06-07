import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CocinaResponse } from 'src/app/core/model/cocinaResponse';
import { CocinaService } from 'src/app/core/services/cocina.service';
import { RestauranteRequest } from 'src/app/core/model/restauranteRequest'
import { RestauranteService } from 'src/app/core/services/restaurante.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-restaurantform',
  templateUrl: './restaurantform.component.html',
  styleUrls: ['./restaurantform.component.css']
})
export class RestaurantformComponent implements OnInit {

  cocinas: CocinaResponse[] = [];
  cocinasSelected: CocinaResponse[] = [];
  file: File | undefined = undefined;
  showSuccess: boolean = false;
  showFailure: boolean = false;

  
  restForm = new FormGroup({
    nombre: new FormControl('', Validators.required),
    descripcion: new FormControl('', Validators.required),
    imagen: new FormControl('', Validators.required),
    apertura: new FormControl('', Validators.required),
    cierre: new FormControl('', Validators.required),
  });

  constructor(private cocinaService: CocinaService, private restaurantService: RestauranteService, private router: Router) { }

  ngOnInit(): void {
    this.cocinaService.getAll().subscribe(res => this.cocinas = res);
  }

  toggleCocina(cocina: CocinaResponse) {
    if(this.cocinasSelected.includes(cocina)){
     this.cocinasSelected.splice(this.cocinasSelected.indexOf(cocina, 0), 1);
    }else {
      this.cocinasSelected.push(cocina);
    }
  }

  onSubmit(){
    let restauranteNuevo: RestauranteRequest = {
      nombre: this.restForm.value.nombre!,
      apertura: this.restForm.value.apertura!,
      cierre: this.restForm.value.cierre!,
      descripcion: this.restForm.value.descripcion!,
      cocinas: this.cocinasSelected.map(c => c.id)
    }
    this.restaurantService.create(restauranteNuevo, this.file!).subscribe(response => {
      this.showSuccess = true;
      setTimeout(() => {
        this.router.navigate(['restaurantes'])
      }, 2000);
      
    });
  }

  onFilechange(event: any) {
    console.log(event.target.files[0])
    this.file = event.target.files[0]
  }

}
