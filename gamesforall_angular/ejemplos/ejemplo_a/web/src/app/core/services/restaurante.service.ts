import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageDTO } from '../model/pageDTO';
import { environment } from 'src/environments/environment';
import { RestauranteResponse } from '../model/restauranteResponse';
import { RestauranteRequest } from '../model/restauranteRequest';

const mapping = "restaurante"

@Injectable({
  providedIn: 'root'
})
export class RestauranteService {

  constructor(private http: HttpClient) { }

  public getManaged(): Observable<PageDTO<RestauranteResponse>> {
    return this.http.get<PageDTO<RestauranteResponse>>(`${environment.URL_BASE_API}/${mapping}/managed`);
  }

  public create(restauranteNuevo: RestauranteRequest, imagen: File): Observable<RestauranteResponse> {
    let formData = new FormData();
    formData.append('file', imagen, imagen.name);
    formData.append('body', new Blob([JSON.stringify(restauranteNuevo)], {
      type: "application/json"
    }));
    return this.http.post<RestauranteResponse>(`${environment.URL_BASE_API}/${mapping}/`, formData)
  }


}
