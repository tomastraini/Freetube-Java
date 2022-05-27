import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  constructor(private http: HttpClient, public router: Router, private appComponent: AppComponent) { }

  userImg: any;
  loggedIn: any;
  userInfo: any;
  userName = '';
  videosOriginal: any;
  videos: any;
  permissionToDelete: any;
  vidToDelete = 0;

  breakpoint = 6;
  length = 0;
  pageSize = 6;
  pageSizeOptions = [6];
  @Input() busquedavalue: any;

  reload(): void
  {
    const actualroute = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigateByUrl(actualroute);
    });
  }

  ngOnInit(): void
  {
    const user = sessionStorage.getItem('x');
    const password = sessionStorage.getItem('y');
    this.loggedIn = user !== 'user' && password !== '123';
    if (this.router.url.includes('/profile'))
    {
      const id = this.router.url.split('/')[2];
      if (id === sessionStorage.getItem('m')) { this.permissionToDelete = true; }
      this.userImg = this.appComponent.apiUrl + 'Users?id_user=' + id;

      this.userName = this.userInfo.usern;
      this.http.get(this.appComponent.apiUrl + 'videos',
      {
      headers: {
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
        }
      }).subscribe(
      (Response) =>
      {
        this.videosOriginal = Response;
        this.videos = Response;
        const usernames = this.userName;
        this.videos = this.videos.filter((video: any) => video.usern === usernames);
        this.videosOriginal.forEach((value: any) =>
        {
          if (value.description == null)
          {
            value.description = '';
          }
          value.descriptionLength = value.description != null ? value.description.length : 0;

          value.linksrc = this.appComponent.apiUrl + 'videos/watch/?id=' + value.id_video;

          value.imglinksrc = this.appComponent.apiUrl + 'Users?id_user=' + value.id_user;

        });
      });
    }


  }

  OnPageChange(event: PageEvent): void{
    const startIndex = event.pageIndex * event.pageSize;
    let endIndex = startIndex + event.pageSize + 3;
    if (endIndex > this.length){
      endIndex = this.length;
    }
    this.videos = this.videosOriginal;
    this.videos = this.videos.slice(startIndex, endIndex);
  }
  onResize(event: any): void
  {
    this.breakpoint = (event.target.innerWidth <= 400) ? 1 : 6;
  }
  openVid($event: any, video: any): void
  {
    if ($event.type === 'click')
    {
      window.location.href = '/watch/' + video.id_video;
    }
    else if ($event.type === 'auxclick')
    {
      window.open('/watch/' + video.id_video, '_blank');
    }
  }

  selectedVideoDelete(id_video: any): void
  {
    this.vidToDelete = id_video;
  }

  deleteVideo(): void
  {
    this.http.delete(this.appComponent.apiUrl + 'videos?id=' + this.vidToDelete,
    {
      observe: 'response',
      responseType: 'text',
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
      })}).subscribe(data => {
          this.videos = this.videos.filter((video: any) => video.id_video !== this.vidToDelete);
          this.videosOriginal = this.videosOriginal.filter((video: any) => video.id_video !== this.vidToDelete);
          this.vidToDelete = 0;
          window.location.reload();
      });

    window.location.reload();
  }

  goEditProfile(): void
  {
    window.location.href = '/editProfile';
  }
}
