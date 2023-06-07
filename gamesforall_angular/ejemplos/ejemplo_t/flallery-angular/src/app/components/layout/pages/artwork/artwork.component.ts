
import { ModalArtworkComponent } from '../../modals/modal-artwork/modal-artwork.component';
import { MatDialog } from '@angular/material/dialog';
import { ArtworkService } from 'src/app/services/artwork.service';
import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { UtilService } from 'src/app/reutilizable/util.service';
import Swal from 'sweetalert2';
import { UserResponse } from 'src/app/models/response-dtos/create-user-response.interface';
import { ArtworkCategoryService } from 'src/app/services/artwork-category.service';
import { ArtworkResponse } from 'src/app/models/response-dtos/artwork-response-list.interface';
import { ArtworkDetailsDialogComponent } from '../../modals/artwork-details-dialog/artwork-details-dialog.component';
import { ArtworkEditDialogComponent } from '../../modals/artwork-edit-dialog/artwork-edit-dialog.component';
import { CommentRequest } from 'src/app/models/request-dtos/comment-create-request.interface';



@Component({
  selector: 'app-artwork',
  templateUrl: './artwork.component.html',
  styleUrls: ['./artwork.component.css']
})
export class ArtworkComponent {

  columnasTable: string[] = ['id', 'categoria', 'fullName', 'createdAt', 'acciones'];
  artworks: ArtworkResponse[] = [];
  filteredArtworks: ArtworkResponse[] = [];

  dataListaArtworks = new MatTableDataSource(this.artworks);
  @ViewChild(MatPaginator) paginacionTabla!: MatPaginator;

  constructor(
    private artworkCategoryService: ArtworkCategoryService,
    private dialog: MatDialog,
    private artworkService: ArtworkService,
    private _utilService: UtilService) { }


  ngOnInit(): void {
    this.loadArtworks();
    this.filteredArtworks = this.artworks;
  }


  applyFilterCard(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredArtworks = this.artworks.filter((artwork) => {
      return (
        artwork.name.toLowerCase().includes(filterValue) ||
        artwork.categoryName.toLowerCase().includes(filterValue) ||
        artwork.owner.toLowerCase().includes(filterValue)
      );
    });
    if(this.filteredArtworks.length=== 0){
      this._utilService.mostrarAlerta("No se han encontrado resultados","Oops!")
    }
  }


  loadArtworks(): void {
    this.artworks = [];
    this.artworkCategoryService.getAllCategories().subscribe(
      response => {
        response.forEach(c => {
          if (c.artworkResponseList!=null){
            c.artworkResponseList.forEach(a => this.artworks.push(a))
      }});
      },
    );
  }


  aplicarFiltroTabla(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataListaArtworks.filter = filterValue.trim().toLocaleLowerCase();
  }


  openDetailsDialog(artwork: ArtworkResponse): void {
    this.dialog.open(ArtworkDetailsDialogComponent, {
      width: '70%',
      data: artwork
    });
  }


  openEditDialog(artwork: ArtworkResponse): void {
    this.dialog.open(ArtworkEditDialogComponent, {
      width: '500px',
      disableClose: true,
      data: artwork
    }).afterClosed().subscribe(res => {
      if (res == "true") this.loadArtworks();
    });
  }

  deleteArtwork(artwork: ArtworkResponse) {
    Swal.fire({
      title: '¿Desea eliminar el artwork?',
      text: artwork.name,
      icon: "warning",
      confirmButtonColor: '#3085d6',
      confirmButtonText: "Si, eliminar",
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'No, volver'
    }).then((res) => {
      if (res.isConfirmed) {

        this.artworkService.deleteArtwork(artwork.uuid).subscribe({
          next: (data) => {
            this._utilService.mostrarAlerta("El artwork fue eliminado", "Listo!");
            this.artworks = this.artworks.filter(a => a.uuid !== artwork.uuid); // Remove the deleted artwork from the artworks array
           this.filteredArtworks = this.filteredArtworks.filter(a => a.uuid !== artwork.uuid); // Remove the deleted artwork from the filteredArtworks array

          },
        })

      }
    })
  }


  commentArtwork(artwork: ArtworkResponse): void {
    Swal.fire({
      title: "Add Comment",
      text: "Enter your comment",
      input: 'text',
      showCancelButton: true
  }).then((result) => {
    if (result.isConfirmed) {
      const comment: CommentRequest = {
        text: result.value
      };
      if (result.value.trim()!=''){
        this.artworkService.addComment(artwork.uuid,comment).subscribe(
          () => {
            Swal.fire('Comment added!', 'Your comment has been submitted successfully.', 'success');
          },
        ); this.dataListaArtworks.filter = ''.trim().toLocaleLowerCase();
      } else{
        Swal.fire('Error', 'You can not submit a empty comment. Please try again.', 'error');
        this.dataListaArtworks.filter = ''.trim().toLocaleLowerCase();
      }
    }
  });
  /*    Swal.({
      title: 'Add Comment',
      input: 'text',
      inputPlaceholder: 'Enter your comment',
      showCancelButton: true,
      confirmButtonText: 'Submit',
      cancelButtonText: 'Cancel',
      inputValidator: (value) => {
        if (!value) {
          return 'Please enter a comment';
        }
      }
    }).then((result) => {
      if (result.isConfirmed) {
        const comment: CommentRequest = {
          text: result.value
        };
        this.artworkService.addComment(artwork.uuid,comment).subscribe(
          () => {
            Swal.fire('Comment added!', 'Your comment has been submitted successfully.', 'success');
          },
          (error) => {
            Swal.fire('Error', 'Failed to submit comment. Please try again.', 'error');
          }
        );
      }
    });*/
  }


  openCreateArtworkDialog() {
    const dialogRef = this.dialog.open(ModalArtworkComponent, {
      width: '500px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const newArtwork = result.artwork;
        const selectedFile = result.file;
        this.artworkService.createArtwork(newArtwork, selectedFile).subscribe(
          response => {
            this._utilService.mostrarAlerta("El artwork fue registrado", "Éxito")
            this.loadArtworks()
          }
        )
      }
    });
  }
}

