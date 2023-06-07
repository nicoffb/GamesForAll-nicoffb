import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PageDTO } from '../model/pageDTO';
import { PlatoResponse } from '../model/plato';
import { RestauranteResponse } from '../model/restauranteResponse';

const mapping = "plato"

@Injectable({
  providedIn: 'root'
})
export class PlatoService {

  constructor(private http: HttpClient) { }

  public getFromRestaurante(id: String, page: number, size: number): Observable<PageDTO<PlatoResponse>> {
    return this.http.get<PageDTO<PlatoResponse>>(`${environment.URL_BASE_API}/${mapping}/restaurante/${id}?page=${page}&size=${size}`);
  }

  public delete(id: String) {
    return this.http.delete(`${environment.URL_BASE_API}/${mapping}/${id}`);
  }

}
