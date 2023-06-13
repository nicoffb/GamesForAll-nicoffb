import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Category, CategoryList } from "../models/category.interface";
import { Observable } from "rxjs";



@Injectable({
    providedIn: 'root'
  })
  export class CategoryService {
  
    constructor(private http:HttpClient) { }
  
    private urlApi:string = "http://localhost:8080/category/";
  
    getAllCategories(): Observable<CategoryList>{
        return this.http.get<CategoryList>(`${this.urlApi}list`);
    }
  
    editCategory(id: number, category: Category): Observable<Category>{
      return this.http.put<Category>(`${this.urlApi}${id}`,category)
    }
  
    deleteCategory(id: number): Observable<Category>{
      return this.http.delete<Category>(`${this.urlApi}${id}`)
    }
  
    createCategory(category: Category): Observable<Category>{
      return this.http.post<Category>(`${this.urlApi}`,category)
    }
  }
  