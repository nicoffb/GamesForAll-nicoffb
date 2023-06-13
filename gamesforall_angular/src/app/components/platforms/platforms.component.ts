import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Platform } from 'src/app/models/platform.interface';



import { PlatformService } from 'src/app/services/platform.service';


import Swal from 'sweetalert2';

@Component({
  selector: 'app-platforms',
  templateUrl: './platforms.component.html',
  styleUrls: ['./platforms.component.css']
})
export class PlatformsComponent implements OnInit {
  dataListaPlataformas: Platform[] = [];
  columnasTable: string[] = ['id', 'name', 'actions'];

  constructor(private platformService: PlatformService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadPlatforms();
  }

  loadPlatforms(): void {
    this.platformService.getAllPlatforms().subscribe(
      (response: Platform[]) => {
        this.dataListaPlataformas = response
      }
    );
  }


  deletePlatform(platform: Platform) {
    Swal.fire({
      title: '¿Desea eliminar la plataforma?',
      text: platform.platformName,
      icon: "warning",
      confirmButtonColor: '#3085d6',
      confirmButtonText: "Si, eliminar",
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'No, volver'
    }).then((res) => {
      if (res.isConfirmed) {

        this.platformService.deletePlatform(platform.id!).subscribe(
          () => {
            Swal.fire('Platform Deleted!', 'The platform has been deleted successfully.', 'success');
            this.loadPlatforms();
          },
        );
      }
    })
  }


  createPlatform(): void {
    Swal.fire({
      title: "Create New Platform",
      text: "Enter the name of the platform",
      input: 'text',
      showCancelButton: true
    }).then((result) => {
      if (result.isConfirmed) {
        const platform: Partial<Platform> = {
          platformName: result.value
        };
        if (result.value.trim()!=''){
          this.platformService.createPlatform(platform as Platform).subscribe(
            () => {
              Swal.fire('Platform Created!', 'Your new platform has been created successfully.', 'success');
              this.loadPlatforms();
            },
          );
        } else {
          Swal.fire('Error', 'You can not create an empty name platform. Please try again.', 'error');
        }
      }
    });
  }


  editPlatform(platformToEdit: Platform): void {
    Swal.fire({
      title: "Edit Platform Name",
      text: "Enter the new name",
      input: 'text',
      inputValue: platformToEdit.platformName,
      showCancelButton: true
    }).then((result) => {
      if (result.isConfirmed) {
        if (result.value.trim()!=''){
          const platform: Platform = {
            id: platformToEdit.id,
            platformName: result.value
          };
          this.platformService.editPlatform(platform.id!, platform).subscribe(
            () => {
              Swal.fire('Name changed!', 'Your platform has been edited successfully.', 'success');
              this.loadPlatforms();
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
    // Apply filtering logic to update the displayed platforms based on the filter value
  }
}
