import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class UploadVideoService {

  constructor(private http : HttpClient) { 

  }


}
