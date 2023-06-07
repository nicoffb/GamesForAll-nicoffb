import { Component, Input, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { RecipeDetailsResponse } from 'src/app/models/recipe.interface';
import { UserResponse } from 'src/app/models/user.interface';
import { FileService } from 'src/app/services/file.service';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css'],
})
export class RecipeDetailsComponent implements OnInit{
  @Input() recipe: RecipeDetailsResponse = {} as RecipeDetailsResponse;
  
  private _author = new BehaviorSubject<UserResponse>({} as UserResponse);
  @Input() set author(value: UserResponse) {
    this._author.next(value);
  }
  get author() {
    return this._author.getValue();
  }
  
  recipeImgData = '';
  authorAvatarData = ''

  constructor(private fileService: FileService) {}

  ngOnInit(): void {
    this._author.subscribe((val) => {
      this.getAuthorImage(val);
    });
    this.getRecipeImage(this.recipe.img)
  }
  getAuthorImage(val: UserResponse) {
    if (val.avatar != null) {
      this.fileService.getData(val.avatar)
      .subscribe((res) => (this.authorAvatarData = res));
    }
  }

  getRecipeImage(val: string) {
    if (val != null) {
      this.fileService.getData(val)
      .subscribe((res) => (this.recipeImgData = res));
    }
  }
}
