import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-video-watch',
  templateUrl: './video-watch.component.html',
  styleUrls: ['./video-watch.component.scss']
})
export class VideoWatchComponent implements OnInit {

  constructor(public route: ActivatedRoute, private http: HttpClient, public router: Router, private appComponent: AppComponent)
  {}
  id: any;
  video: any;
  src: any;
  comments: any = [];
  liked: any;
  users: any;
  fullVidUsername: any;

  videosOriginal: any;
  videos: any;

  reload(): void
  {
    const actualroute = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigateByUrl(actualroute);
    });
  }

  ngOnInit(): void
  {
      this.id = this.router.url.split('/')[2];
      const newRoute = this.appComponent.apiUrl + 'Videos/' + this.id;

      this.http.get(newRoute,
      {
        headers:
        {
          Authorization: 'Bearer ' + sessionStorage.getItem('token')
        }
      }).subscribe(res => {
          if (res){
            this.video = res;
            const date = new Date(this.video.fecha_carga);
            const now = new Date();
            const diff = now.getTime() - date.getTime();
            const days = Math.floor(diff / (1000 * 60 * 60 * 24));
            const months = Math.floor(diff / (1000 * 60 * 60 * 24 * 30));
            const years = Math.floor(diff / (1000 * 60 * 60 * 24 * 30 * 12));
            if (years > 0)
            {
              this.video.uploaded_since = years + ' años';
            }
            else if (months > 0)
            {
              this.video.uploaded_since = months + ' meses';
            }
            else if (days > 0)
            {
              this.video.uploaded_since = days + ' días';
            }
            else
            {
              this.video.uploaded_since = 'Hace un momento';
            }

            this.video.srcImage = this.appComponent.apiUrl + 'Users?id_user=' + this.video.id_user;

            this.src = this.appComponent.apiUrl + 'Videos/physical?id=' + this.video.id_drive;
            if (sessionStorage.getItem('m') !== undefined && sessionStorage.getItem('m') !== null)
            {
              this.http.post(this.appComponent.apiUrl + 'Videos/getIfLiked?id_video=' +
              this.video.id_video + '&id_user=' + sessionStorage.getItem('m'),
              null,
              {
                headers:
                {
                  Authorization: 'Bearer ' + sessionStorage.getItem('token')
                }
              }).subscribe(res2 => {
                  this.liked = res2;
              });
            }
            else
            {
              this.liked = 3;
            }


            this.http.get(this.appComponent.apiUrl + 'Comments?id_video='  + this.id, {
              headers: {
                Authorization: 'Bearer ' + sessionStorage.getItem('token')
              }
            })
              .subscribe(res3 => {
                if (res3){
                    this.comments = res3;
                    for (let i = 0; i < this.comments.length; i++){
                      const date = new Date(this.comments[i].fecha_carga);
                      const now = new Date();
                      const diff = now.getTime() - date.getTime();
                      const days = Math.floor(diff / (1000 * 60 * 60 * 24));
                      const months = Math.floor(diff / (1000 * 60 * 60 * 24 * 30));
                      const years = Math.floor(diff / (1000 * 60 * 60 * 24 * 30 * 12));
                      if (years > 0){
                        this.comments[i].fecha_diff = years + ' years ago';
                      }
                      else if (months > 0){
                        this.comments[i].fecha_diff = months + ' months ago';
                      }
                      else if (days > 0){
                        this.comments[i].fecha_diff = days + ' days ago';
                      }
                      else{
                        this.comments[i].fecha_diff = 'today';
                      }
                    }
                  }
            });
          }
      });

      this.http.get(this.appComponent.apiUrl + 'Videos/top',
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

  submitComment(event: any): void
  {
    if (sessionStorage.getItem('m') !== null && sessionStorage.getItem('m') !== undefined)
    {
      const newRoute = this.appComponent.apiUrl + 'Comments';
      this.http.post(newRoute,
      {
        id_video: this.id,
        comment: event.target.commentText.value,
        id_user: sessionStorage.getItem('m')
      },
      {
        headers:
        {
          Authorization: 'Bearer ' + sessionStorage.getItem('token')
        }
      }).subscribe(res => {
          if (res){
            this.comments.push(res);
          }
      });
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
      this.router.onSameUrlNavigation = 'reload';
      this.router.navigate(['/watch/' + this.id]);
    }
    else
    {
      this.router.navigate(['/login']);
    }
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
      if ($event.srcElement.className.includes('recommendedVideosViews'))
      {
        window.location.href = '/watch/' + video.id_video;
      }
    }
  }

  like(): void
  {

    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
      }),
      body: {
        id_video: this.id,
        id_user: sessionStorage.getItem('m'),
      },
    };
    if (sessionStorage.getItem('m') !== null && sessionStorage.getItem('m') !== undefined)
    {
      if (this.liked !== 1)
      {
        this.video.likes += 1;
        if (this.liked === 0) { this.video.dislikes -= 1; }
        this.liked = 1;
        this.reload();

        this.http.post(this.appComponent.apiUrl + 'Videos/like',
        {
          id_video: this.id,
          id_user: sessionStorage.getItem('m'),
          liked: true
        },
        {
          headers:
          {
            Authorization: 'Bearer ' + sessionStorage.getItem('token')
          }
        }).subscribe(res => {

        });
      }
      else
      {
        this.liked = 3;
        this.video.likes -= 1;
        this.reload();
        this.http.delete(this.appComponent.apiUrl + 'Videos/like',
        options,
        ).subscribe(res => {

        });
      }
    }
    else
    {
      this.router.navigate(['/login']);
    }
  }

  dislike(): void
  {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
      }),
      body: {
        id_video: this.id,
        id_user: sessionStorage.getItem('m'),
      },
    };
    if (sessionStorage.getItem('m') !== null && sessionStorage.getItem('m') !== undefined)
    {
      if (this.liked !== 0)
      {
        this.video.dislikes += 1;
        if (this.liked === 1) { this.video.likes -= 1; }
        this.liked = 0;
        this.reload();
        this.http.post(this.appComponent.apiUrl + 'Videos/like',
        {
          id_video: this.id,
          id_user: sessionStorage.getItem('m'),
          liked: false
        },
        {
          headers:
          {
            Authorization: 'Bearer ' + sessionStorage.getItem('token')
          }
        }).subscribe(res => {

        });
      }
      else
      {
        this.liked = 3;
        this.video.dislikes -= 1;
        this.reload();
        this.http.delete(this.appComponent.apiUrl + 'Videos/like',
        options).subscribe(res => {

        });
      }

    }
    else
    {
      this.router.navigate(['/login']);
    }
  }

  showProfile(id: any): void
  {
    window.location.href = '/profile/' + id;
  }

  openProfile(id: any): void
  {
    window.location.href = '/profile/' + id;
  }
}
