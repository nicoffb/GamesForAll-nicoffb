import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Category } from 'src/app/models/category.interface';
import { CategoryService } from 'src/app/services/category.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
  dataListaCategorias: Category[] = [];
  columnasTable: string[] = ['id', 'name', 'actions'];

  constructor(private categoryService: CategoryService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe(
      (categories: Category[]) => {
        this.dataListaCategorias = categories.sort((a, b) => a.id! - b.id!);
      }
    );
  }


  deleteCategory(category: Category) {
    Swal.fire({
      title: '¿Desea eliminar la categoría?',
      text: category.genre,
      icon: "warning",
      confirmButtonColor: '#3085d6',
      confirmButtonText: "Si, eliminar",
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'No, volver'
    }).then((res) => {
      if (res.isConfirmed) {

        this.categoryService.deleteCategory(category.id!).subscribe(
          () => {
            Swal.fire('Categoría eliminada', 'La categoría ha sido eliminada con éxito', 'success');
            this.loadCategories();
          },
        );
      }
    })
  }


  createCategory(): void {
    Swal.fire({
      title: "Crear una nueva Categoría",
      text: "Introduzca el nombre de la categoría",
      input: 'text',
      showCancelButton: true
    }).then((result) => {
      if (result.isConfirmed) {
        const category: Partial<Category> = {
          genre: result.value
        };
        if (result.value.trim()!=''){
          this.categoryService.createCategory(category as Category).subscribe(
            () => {
              Swal.fire('Categoría creada', 'Categoría creada exitosamente', 'success');
              this.loadCategories();
            },
          );
        } else {
          Swal.fire('Error', 'No se puede introducir el campo vacío', 'error');
        }
      }
    });
  }


  editCategory(categoryToEdit: Category): void {
    Swal.fire({
      title: "Editar nombre de la Categoría",
      text: "Introduzca el nuevo nombre",
      input: 'text',
      inputValue: categoryToEdit.genre,
      showCancelButton: true
    }).then((result) => {
      if (result.isConfirmed) {
        if (result.value.trim()!=''){
          const category: Category = {
            id: categoryToEdit.id,
            genre: result.value
          };
          this.categoryService.editCategory(category.id!, category).subscribe(
            () => {
              Swal.fire('Nombre cambiado', 'La categoría ha sido editada exitosamente', 'success');
              this.loadCategories();
            },
          );
        } else {
          Swal.fire('Error', 'No se puede introducir el campo vacío', 'error');
        }
      }
    });
  }
  


  applyFilterTable(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    // Apply filtering logic to update the displayed categories based on the filter value
  }
}
