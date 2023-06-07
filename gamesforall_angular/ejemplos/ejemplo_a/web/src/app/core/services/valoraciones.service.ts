import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PageDTO } from '../model/pageDTO';
import { RateResponse } from '../model/rateResponse';

@Injectable({
  providedIn: 'root'
})
export class ValoracionesService {

  constructor(private http: HttpClient) { }

  public getValoraciones(): Observable<PageDTO<RateResponse>> {
    return this.http.get<PageDTO<RateResponse>>(`${environment.URL_BASE_API}/valoraciones/`);
  }

}
