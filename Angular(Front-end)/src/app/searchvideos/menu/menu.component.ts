import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../../app.component';
import { PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
  providers: [
    AppComponent
  ]
})
export class MenuComponent implements OnInit {
  constructor(private http: HttpClient, private app: AppComponent, private router: Router, private appComponent: AppComponent)
  {

  }

  videos: any;
  videosOriginal: any;
  comments: any;
  carpeta: any;
  obj: any;
  videoElement: any;
  mover = 0;
  @Input() busquedavalue: any;

  HasLoaded = false;

  amountSliced = 8;

  ngOnInit(): void
  {
    setInterval(() => {
      if (this.videos !== undefined && this.videos !== null && this.videos.length > 0)
      {
        this.HasLoaded = true;
      }
    }, 1000);

    if (this.router.url.includes('/search'))
    {
      const searchvalue = this.router.url.split('/')[2];
      this.busquedavalue = searchvalue;
    }

    this.http.get(this.appComponent.apiUrl + 'Videos',
      {
      headers: {
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
        }
      }
        )
      .subscribe(
        (Response) =>
        {
          this.videosOriginal = Response;
          this.videos = Response;
          this.videos = this.videos.slice(0, this.amountSliced);
          this.videosOriginal.forEach((value: any) =>
          {
            if (value.description == null)
            {
              value.description = '';
            }
            value.descriptionLength = value.description != null ? value.description.length : 0;

            value.linksrc = this.appComponent.apiUrl + 'Videos/physical?id=' + value.id_drive;

            value.imglinksrc = this.appComponent.apiUrl + 'Users?id_user=' + value.id_user;

          });
        });
  }

  openVid($event: any, video: any): void
  {
    if ($event.type === 'auxclick')
    {
      if ($event.srcElement.className.includes('vidUserIMG'))
      {
        window.open('/profile/' + video.id_user);
      }
      window.open('/watch/' + video.id_video);
    }
    else
    {
      if ($event.srcElement.className.includes('vidUserIMG'))
      {
        this.router.navigate(['/profile/' + video.id_user]);
      }
      if ($event.srcElement.className.includes('ml-2'))
      {
        window.location.href = '/watch/' + video.id_video;
      }
    }
  }

  openProfile(id: any): void
  {
    window.location.href = '/profile/' + id;
  }

  expandView(): void
  {
    if (this.amountSliced + 8 < this.videosOriginal.length)
    {
      this.amountSliced += 8;
      this.videos = this.videosOriginal.slice(0, this.amountSliced);
    }
    else
    {
      this.amountSliced = this.videosOriginal.length;
      this.videos = this.videosOriginal;
    }
  }
}
