import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArtworkCreateRequest } from '../models/request-dtos/artwork-create-request.interface';
import { ArtworkResponse } from '../models/response-dtos/artwork-response-list.interface';
import { CommentRequest } from '../models/request-dtos/comment-create-request.interface';

@Injectable({
  providedIn: 'root'
})
export class ArtworkService {


  private artworkUrl = 'http://localhost:8080/artwork';

  constructor(private http: HttpClient) { }

  createArtwork(artworkRequest: ArtworkCreateRequest, file: File): Observable<any> {

    const formData = new FormData();

    const blobBody = new Blob([JSON.stringify(artworkRequest)], {
      type: "application/vnd.api+json", // Set the appropriate Content-Type header
    });

    formData.append('file', file);
    formData.append('artwork', blobBody); // Convert artworkRequest to JSON string

    return this.http.post(this.artworkUrl, formData);
  }

  getUserArtworks(): Observable<ArtworkResponse[]> {

    return this.http.get<ArtworkResponse[]>('http://localhost:8080/user/artwork/');
  }

  deleteArtwork(id: string): Observable<ArtworkResponse[]> {

    return this.http.delete<any>(`http://localhost:8080/artwork/${id}`);
  }

  addComment(artworkId: string, comment: CommentRequest): Observable<any> {
    const url = `http://localhost:8080/artwork/${artworkId}/comment`;
    return this.http.post(url, comment);
  }
}



