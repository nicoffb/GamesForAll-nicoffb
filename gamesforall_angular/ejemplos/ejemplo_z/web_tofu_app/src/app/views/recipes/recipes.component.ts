import { Component, OnInit } from '@angular/core';
import { mergeMap, tap } from 'rxjs';
import { IngredientResponse } from 'src/app/models/ingredient.interface';
import {
  RecipeDetailsResponse,
  RecipeResponse,
} from 'src/app/models/recipe.interface';
import { IngredientService } from 'src/app/services/ingredient.service';
import { RecipeService } from 'src/app/services/recipe.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css'],
})
export class RecipesComponent implements OnInit {
  recipeList: RecipeResponse[] = [];
  ingredientList: IngredientResponse[] = [];
  recipeDetails = {} as RecipeDetailsResponse;
  indexRecipes = 0;

  //TODO Posibilidad de añadir una receta
  //TODO Posibilidad de borrar una receta seleccionada
  //TODO Posibilidad de editar una receta seleccionada
  //TODO Hacer las listas de recetas con ingredientes infinitas

  constructor(
    private recipeService: RecipeService,
    private ingredientService: IngredientService
  ) {}

  ngOnInit(): void {
    this.getRecipes();
  }

  getRecipes() {
    this.recipeService
      .getRecipesBySearch('', this.indexRecipes)
      .subscribe((res) => {
        if (res.totalPages >= this.indexRecipes) {
          this.recipeList = res.content;
        }
      });
  }

  getRecipeIngredients(recipe: RecipeResponse) {
    this.ingredientService.getRecipeIngredients(recipe).subscribe((res) => {
      this.ingredientList = res.content;
    });
  }

  getRecipeDetails(recipe: RecipeResponse) {
    this.recipeService.getRecipeDetailsById(recipe.id).subscribe((res) => {
      this.recipeDetails = res;
    });
  }
}
