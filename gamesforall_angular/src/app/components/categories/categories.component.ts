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
        const category: Partial<Category> = {
          genre: result.value
        };
        if (result.value.trim()!=''){
          this.categoryService.createCategory(category as Category).subscribe(
            () => {
              Swal.fire('Category Created!', 'Your new category has been created successfully.', 'success');
              this.loadCategories();
            },
          );
        } else {
          Swal.fire('Error', 'You can not create an empty name category. Please try again.', 'error');
        }
      }
    });
  }


  editCategory(categoryToEdit: Category): void {
    Swal.fire({
      title: "Edit Category Name",
      text: "Enter the new name",
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
              Swal.fire('Name changed!', 'Your category has been edited successfully.', 'success');
              this.loadCategories();
            },
          );
        } else {
          Swal.fire('Error', 'You can not submit a empty name. Please try again.', 'error');
        }
      }
    });
  }
  


  applyFilterTable(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    // Apply filtering logic to update the displayed categories based on the filter value
  }
}
