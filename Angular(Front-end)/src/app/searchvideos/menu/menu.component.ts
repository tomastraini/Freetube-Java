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
  LoadingMessage = 'Cargando página...';
  amountSliced = 8;

  heaviestVideoWeight = 0;
  heaviestVideoID = 0;
  
  ngOnInit(): void
  {
    let i = 0;
    setInterval(() => {
      i++;
      if (this.videos !== undefined && this.videos !== null && this.videos.length > 0)
      {
        this.HasLoaded = true;
      }
      if (i > 2)
      {
        this.LoadingMessage = 'Autenticando...';
      }
      if (i > 5)
      {
        this.LoadingMessage = 'Cargando videos...';
      }
      if (i > 7)
      {
        this.LoadingMessage = 'Cargando fotos de perfil...';
      }
      if (i > 10)
      {
        this.LoadingMessage = 'Cargando datos...';
      }
      if (i > 25)
      {
        this.LoadingMessage = 'Descargando videos...';
      }
      if (i > 35)
      {
        this.LoadingMessage = 'Seguimos descargando videos, por favor espere...';
      }
    }, 1000);


    if (this.router.url.includes('/search'))
    {
      const searchvalue = this.router.url.split('/')[2];
      this.busquedavalue = searchvalue;
    }

    this.http.get<any[]>(this.appComponent.apiUrl + 'Videos',
      {
        observe: 'response',
      headers: {
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
        }
      }
        )
      .subscribe(
        (Response) =>
        {
          this.videosOriginal = Response.body;
          this.videos = Response.body;
          this.videos = this.videos.slice(0, this.amountSliced);

          this.videosOriginal.forEach((value: any) =>
          {
            if (value.description == null)
            {
              value.description = '';
            }
            value.descriptionLength = value.description != null ? value.description.length : 0;

            value.linksrc = this.appComponent.apiUrl + 'Videos/physical?id=' + value.id_thumbnail;

            value.imglinksrc = this.appComponent.apiUrl + 'Users?id_user=' + value.id_user;

            value.videoWeight = parseInt(value.videoWeight, 10);
            if (value.videoWeight > this.heaviestVideoWeight)
            {
              this.heaviestVideoWeight = value.videoWeight;
              this.heaviestVideoID = value.id_video;
            }
          });
        },
        (error) =>
        {
          setTimeout(() => {
            window.location.reload();
          }, 3000);
        }
        );
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
