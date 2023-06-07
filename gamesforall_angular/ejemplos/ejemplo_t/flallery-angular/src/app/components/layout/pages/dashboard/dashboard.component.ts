import { Component } from '@angular/core';
import { ArtworkResponse } from 'src/app/models/response-dtos/artwork-response-list.interface';
import { JwtUserResponse } from 'src/app/models/response-dtos/login-response.interface';
import { ArtworkDetailsDialogComponent } from '../../modals/artwork-details-dialog/artwork-details-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { UtilService } from 'src/app/reutilizable/util.service';
import { ArtworkCategoryService } from 'src/app/services/artwork-category.service';
import { ArtworkService } from 'src/app/services/artwork.service';
import { ArtworkEditDialogComponent } from '../../modals/artwork-edit-dialog/artwork-edit-dialog.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  user: JwtUserResponse | null = null;
  userArtworks: ArtworkResponse[] = [];

  constructor(
    private artworkCategoryService: ArtworkCategoryService,
    private dialog: MatDialog,
    private artworkService: ArtworkService,
    private _utilService: UtilService) { }
    
  ngOnInit(): void {
    const userJson = localStorage.getItem('user');


    this.artworkService.getUserArtworks().subscribe(
      (artworks: ArtworkResponse[]) => {
        this.userArtworks = artworks;
      },
    );

    if (userJson) {
      this.user = JSON.parse(userJson);
    }
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
      data: artwork
    });
  }
}
