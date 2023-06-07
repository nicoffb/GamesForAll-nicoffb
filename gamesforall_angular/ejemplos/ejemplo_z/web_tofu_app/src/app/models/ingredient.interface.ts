import { UserResponse } from "./user.interface";

export interface RecipeIngredientResponse {
    ingredient: IngredientResponse;
    amount:     number;
    unit:       string; 
    recipeName: string;
}

export interface IngredientResponse {
    id:          string;
    name:        string;
    img:         string;
    description: string;
    author:      UserResponse;
}