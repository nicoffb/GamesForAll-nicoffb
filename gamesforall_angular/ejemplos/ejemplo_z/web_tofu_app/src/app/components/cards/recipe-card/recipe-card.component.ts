import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RecipeResponse } from 'src/app/models/recipe.interface';
import { FileService } from 'src/app/services/file.service';

@Component({
  selector: 'app-recipe-card',
  templateUrl: './recipe-card.component.html',
  styleUrls: ['./recipe-card.component.css']
})
export class RecipeCardComponent {

  @Input() recipe = {} as RecipeResponse;

  @Output() OnClick = new EventEmitter();

  recipeImgData = '';
  authorAvatarData = '';


  constructor(private fileService: FileService) {}
  ngOnInit(): void {
    this.getRecipeImage(this.recipe.img);
    this.getAuthorAvatar(this.recipe.author.avatar)
  }

  emitEvent() {
    this.OnClick.emit();
  }

  getRecipeImage(img: string) {
    if (img != null) {
      this.fileService
        .getData(img)
        .subscribe((res) => (this.recipeImgData = res));
    }
  }

  getAuthorAvatar(avatar: string) {
    if (avatar != null) {
      this.fileService
        .getData(avatar)
        .subscribe((res) => (this.authorAvatarData = res));
    }
  }
}
