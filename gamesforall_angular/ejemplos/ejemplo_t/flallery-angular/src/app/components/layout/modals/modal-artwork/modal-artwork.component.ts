import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ArtworkCreateRequest } from 'src/app/models/request-dtos/artwork-create-request.interface';
import { CreateUserRequest } from 'src/app/models/request-dtos/create-user-request.interface';
import { ArtworkCategory, ArtworkCategoryList } from 'src/app/models/response-dtos/artwork-category-response';
import { ArtworkCategoryService } from 'src/app/services/artwork-category.service';

@Component({
  selector: 'app-modal-artwork',
  templateUrl: './modal-artwork.component.html',
  styleUrls: ['./modal-artwork.component.css']
})
export class ModalArtworkComponent {

  artwork: ArtworkCreateRequest = {
    name: '',
    description: '',
    categoryName: ''
  };
  selectedFile: File | null = null;

  constructor(
    public dialogRef: MatDialogRef<ModalArtworkComponent>,
    private artworkCategoryService: ArtworkCategoryService,
    @Inject(MAT_DIALOG_DATA) public datosArtwork: ArtworkCreateRequest
  ) {}

  artworkCategoryList: ArtworkCategoryList = [];
  ngOnInit(): void {
    this.artworkCategoryService.getAllCategories().subscribe(res=> this.artworkCategoryList = res)
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.selectedFile = file;
  }
  
  createArtwork() {
    // Perform validation or additional processing if needed
    // Create the artwork object
    const newArtwork: ArtworkCreateRequest = {
      name: this.artwork.name,
      description: this.artwork.description,
      categoryName: this.artwork.categoryName
    };

    // Pass the new artwork object and selected file back to the caller
    this.dialogRef.close({ artwork: newArtwork, file: this.selectedFile });
  }

  cancel() {
    this.dialogRef.close();
  }
}

  /*
  createArtworkForm: FormGroup;
  ocultarPassword: boolean = true;
  tituloAccion: string = "Agregar";
  botonAccion: string = "Guardar";
  listaCategorias: ArtworkCategory[] = [];

  constructor(
    private modalActual: MatDialogRef<ModalArtworkComponent>,
    @Inject(MAT_DIALOG_DATA) public datosArtwork: CreateUserRequest,
    private fb: FormBuilder
  ){} */

