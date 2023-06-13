import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Category, CategoryList } from "../models/category.interface";
import { Observable } from "rxjs";
import { Platform, PlatformList } from "../models/platform.interface";




@Injectable({
    providedIn: 'root'
  })
  export class PlatformService {
  
    constructor(private http:HttpClient) { }
  
    private urlApi:string = "http://localhost:8080/platform/";
  
    getAllPlatforms(): Observable<PlatformList>{
        return this.http.get<PlatformList>(`${this.urlApi}list`);
    }
  
    editPlatform(id: number, platform: Platform): Observable<Platform>{
      return this.http.put<Platform>(`${this.urlApi}${id}`,platform)
    }
  
    deletePlatform(id: number): Observable<Platform>{
      return this.http.delete<Platform>(`${this.urlApi}${id}`)
    }
  
    createPlatform(platform: Platform): Observable<Platform>{
      return this.http.post<Platform>(`${this.urlApi}`,platform)
    }
  }
  