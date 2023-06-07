import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CocinaResponse } from '../model/cocinaResponse';

const mapping = "cocina"

@Injectable({
  providedIn: 'root'
})
export class CocinaService {

  constructor(private http: HttpClient) { }

  public getAll(): Observable<CocinaResponse[]> {
    return this.http.get<CocinaResponse[]>(`${environment.URL_BASE_API}/${mapping}/`);
  }

}
