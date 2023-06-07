import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ArtworkResponse } from 'src/app/models/response-dtos/artwork-response-list.interface';

@Component({
  selector: 'app-artwork-edit-dialog',
  templateUrl: './artwork-edit-dialog.component.html',
  styleUrls: ['./artwork-edit-dialog.component.css']
})
export class ArtworkEditDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ArtworkEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public artwork: ArtworkResponse
  ) { }

  
}
