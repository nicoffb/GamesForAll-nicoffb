import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IngredientResponse } from 'src/app/models/ingredient.interface';
import { FileService } from 'src/app/services/file.service';

@Component({
  selector: 'app-ingredient-card',
  templateUrl: './ingredient-card.component.html',
  styleUrls: ['./ingredient-card.component.css'],
})
export class IngredientCardComponent implements OnInit {
  @Input() ingredient = {} as IngredientResponse;

  @Output() OnClick = new EventEmitter();

  ingredientImgData = '';
  authorAvatarData = '';


  constructor(private fileService: FileService) {}
  ngOnInit(): void {
    this.getIngredientImage(this.ingredient.img);
    this.getAuthorAvatar(this.ingredient.author.avatar)
  }

  emitEvent() {
    this.OnClick.emit();
  }

  getIngredientImage(img: string) {
    if (img != null) {
      this.fileService
        .getData(img)
        .subscribe((res) => (this.ingredientImgData = res));
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
