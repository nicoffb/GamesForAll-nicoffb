import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ArtworkCategory, ArtworkCategoryList } from '../models/response-dtos/artwork-category-response';
import { environemnt } from 'src/environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { ArtworkCategoryCreateRequest } from '../models/request-dtos/category-create-request.interface';

@Injectable({
  providedIn: 'root'
})
export class ArtworkCategoryService {

  constructor(private http:HttpClient) { }

  private urlApi:string = "http://localhost:8080/category/";

  getAllCategories(): Observable<ArtworkCategoryList>{
    return this.http.get<ArtworkCategoryList>(this.urlApi)
  }

  editCategory(id: string, category: ArtworkCategoryCreateRequest): Observable<ArtworkCategory>{
    return this.http.put<ArtworkCategory>(`${this.urlApi}${id}`,category)
  }

  deleteCategory(name: string): Observable<ArtworkCategory>{
    return this.http.delete<ArtworkCategory>(`${this.urlApi}${name}`)
  }

  createCategory(category: ArtworkCategoryCreateRequest): Observable<ArtworkCategory>{
    return this.http.post<ArtworkCategory>(`${this.urlApi}`,category)
  }
}
