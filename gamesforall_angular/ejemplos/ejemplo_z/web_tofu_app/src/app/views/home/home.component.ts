import { Component, OnInit } from '@angular/core';
import { finalize, merge, mergeMap } from 'rxjs';
import { IngredientResponse } from 'src/app/models/ingredient.interface';
import { RecipeResponse } from 'src/app/models/recipe.interface';
import { UserDetailsResponse } from 'src/app/models/user.interface';
import { AuthService } from 'src/app/services/auth.service';
import { IngredientService } from 'src/app/services/ingredient.service';
import { RecipeService } from 'src/app/services/recipe.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  currentUser: UserDetailsResponse = {} as UserDetailsResponse;
  recipeList: RecipeResponse[] = [];
  ingredientList: IngredientResponse[] = [];

  constructor(
    private userService: UserService,
    private recipeService: RecipeService,
    private ingredientService: IngredientService
  ) {}

  ngOnInit(): void {
    this.getCurrentUser();
  }

  getCurrentUser() {
    this.userService
      .getCurrentUser()
      .pipe(
        mergeMap((res) => {
          //* SAVE USER AND GET RECIPES
          this.currentUser = res;
          return this.recipeService.getRecipesByAuthor(
            this.currentUser.username
          );
        }),
        mergeMap((res) => {
          //* SAVE RECIPES AND GET INGREDIENTS
          this.recipeList = res.content;
          return this.ingredientService.getIngredientsByAuthor(
            this.currentUser.username
          );
        })
      )
      .subscribe((res) => (this.ingredientList = res.content));
  }

  ingredientClick(){
    console.log("pito")
  }
}
