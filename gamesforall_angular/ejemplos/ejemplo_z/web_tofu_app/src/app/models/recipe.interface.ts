import { CategoryResponse } from "./category.interface";
import { RecipeIngredientResponse } from "./ingredient.interface";
import { Step } from "./step.interface";
import { UserResponse } from "./user.interface";

export interface RecipeResponse {
    id:         string;
    name:       string;
    img:        string;
    type:       string;
    prepTime:   number;
    author:     UserResponse;
    categories: CategoryResponse[];
    createdAt:  string;
    nlikes:     number;
}

export interface RecipeDetailsResponse {
    id:          string;
    name:        string;
    description: string;
    img:         string;
    type:        null;
    prepTime:    number;
    categories:  CategoryResponse[];
    ingredients: RecipeIngredientResponse[];
    author:      UserResponse;
    steps:       Step[];
    createdAt:   string;
    nlikes:      number;
}




