import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ArtworkCategoryCreateRequest } from 'src/app/models/request-dtos/category-create-request.interface';
import { ArtworkCategory } from 'src/app/models/response-dtos/artwork-category-response';
import { ArtworkCategoryService } from 'src/app/services/artwork-category.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-artwork-categorias',
  templateUrl: './artwork-categorias.component.html',
  styleUrls: ['./artwork-categorias.component.css']
})
export class ArtworkCategoriasComponent implements OnInit {
  dataListaCategorias: ArtworkCategory[] = [];
  columnasTable: string[] = ['name', 'artworkCount', 'actions'];

  constructor(private artworkCategoryService: ArtworkCategoryService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.artworkCategoryService.getAllCategories().subscribe(
      (response: ArtworkCategory[]) => {
        this.dataListaCategorias = response;
      },
    );
  }


  deleteCategory(artworkCategory: ArtworkCategory) {
    Swal.fire({
      title: '¿Desea eliminar la categoría?',
      text: artworkCategory.name,
      icon: "warning",
      confirmButtonColor: '#3085d6',
      confirmButtonText: "Si, eliminar",
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'No, volver'
    }).then((res) => {
      if (res.isConfirmed) {

        this.artworkCategoryService.deleteCategory(artworkCategory.name).subscribe(
          () => {
            Swal.fire('Category Deleted!', 'The category has been deleted successfully.', 'success');
            this.loadCategories();
          },
        );
      }
    })
  }


  createCategory(): void {
    Swal.fire({
      title: "Create New Category",
      text: "Enter the name of the category",
      input: 'text',
      showCancelButton: true
  }).then((result) => {
    if (result.isConfirmed) {
      const artworkCategory: ArtworkCategoryCreateRequest = {
        name: result.value
      };
      if (result.value.trim()!=''){
        this.artworkCategoryService.createCategory(artworkCategory).subscribe(
          () => {
            Swal.fire('Category Created!', 'Your new category has been created successfully.', 'success');
            this.loadCategories();
          },
        );
      } else {
        Swal.fire('Error', 'You can not create a empty name category. Please try again.', 'error');
      }}
  });
  }

  editCategory(category: ArtworkCategory): void {
    Swal.fire({
      title: "Edit Category Name",
      text: "Enter the new name",
      input: 'text',
      showCancelButton: true
  }).then((result) => {
    if (result.isConfirmed) {
      const artworkCategory: ArtworkCategoryCreateRequest = {
        name: result.value
      };
      if (result.value.trim()!=''){
        this.artworkCategoryService.editCategory(category.name,artworkCategory).subscribe(
          () => {
            Swal.fire('Name changed!', 'Your category has been edited successfully.', 'success');
            this.loadCategories();
          },
        );
      } else {
        Swal.fire('Error', 'You can not submit a empty name. Please try again.', 'error');
      }}
  });
  }


  applyFilterTable(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    // Apply filtering logic to update the displayed categories based on the filter value
  }
}
