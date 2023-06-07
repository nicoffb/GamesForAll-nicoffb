import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RecipeDetailsResponse, RecipeResponse } from '../models/recipe.interface';
import { Page } from '../models/page.interface';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  constructor(private http: HttpClient) {}


  public getRecipesBySearch(search: string, page =0): Observable<Page<RecipeResponse>> {
      return this.http.get<Page<RecipeResponse>>(
        `${environment.API_URL}/recipe?search=${search}&page=${page}`
      );
  }

  public getRecipesByAuthor(username:string, page=0): Observable<Page<RecipeResponse>>{
    return this.http.get<Page<RecipeResponse>>(
      `${environment.API_URL}/recipe/author/${username}?page=${page}`
    );
  }

  public getRecipeDetailsById(recipeId: string): Observable<RecipeDetailsResponse>{
    return this.http.get<RecipeDetailsResponse>(
      `${environment.API_URL}/recipe/${recipeId}`
    );
  }
}
