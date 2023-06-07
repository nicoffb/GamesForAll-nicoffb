import { Injectable } from '@angular/core';
import { environemnt } from 'src/environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserList } from '../models/response-dtos/create-user-response.interface';
import { PageDto, VentaResponse } from '../models/response-dtos/venta-response.interface';

@Injectable({
  providedIn: 'root'
})
export class VentaService {

  private baseUrl = 'http://localhost:8080/venta';

  constructor(private http: HttpClient) {}

  getListaVentas(page: number, pageSize: number, search: string): Observable<PageDto<VentaResponse>> {
    let params = new HttpParams()
      .set('s', search)
      .set('page', page.toString())
      .set('size', pageSize.toString());

    return this.http.get<PageDto<VentaResponse>>(this.baseUrl, { params });
  }
}
