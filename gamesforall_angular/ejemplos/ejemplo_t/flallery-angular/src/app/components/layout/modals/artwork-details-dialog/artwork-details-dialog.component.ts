import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ArtworkResponse } from 'src/app/models/response-dtos/artwork-response-list.interface';

@Component({
  selector: 'app-artwork-details-dialog',
  templateUrl: './artwork-details-dialog.component.html',
  styleUrls: ['./artwork-details-dialog.component.css']
})
export class ArtworkDetailsDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ArtworkDetailsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public artwork: ArtworkResponse
  ) { }

  closeDialog(): void {
    this.dialogRef.close();
  }
}

