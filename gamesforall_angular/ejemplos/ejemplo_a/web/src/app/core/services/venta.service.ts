import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { VentaResponseOverview } from '../model/ventaResponse';
import { PageDTO } from '../model/pageDTO';



@Injectable({
  providedIn: 'root'
})
export class VentaService {

  constructor(private http: HttpClient) { }

  public getSales(): Observable<PageDTO<VentaResponseOverview>>{
    return this.http.get<PageDTO<VentaResponseOverview>>(`${environment.URL_BASE_API}/venta/`);
  }


}
