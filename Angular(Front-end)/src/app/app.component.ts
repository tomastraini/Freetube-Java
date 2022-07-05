import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Angular';
  url: any;
  format: any;
  filename: any;
  url2: any;
  errorformat = false;
  video: any;
  busquedavalue: any;

  apiUrl = 'https://freetubejava.herokuapp.com/api/';
  mensajerror: any;

  constructor(private http: HttpClient, private router: Router) {

  }

  ngOnInit(): void
  {
    if (sessionStorage.getItem('x') === undefined || sessionStorage.getItem('y') === undefined
    || sessionStorage.getItem('x') === undefined || sessionStorage.getItem('y') === null)
    {
      sessionStorage.setItem('x', 'user');
      sessionStorage.setItem('y', '123');
      window.location.reload();
    }
    if ((sessionStorage.getItem('x')?.includes('user') && sessionStorage.getItem('y')?.includes('123')))
    {
      this.http.post<any>(this.apiUrl + 'authenticate', {
            username: 'user',
            password: '123',
          }, {observe: 'response', responseType: 'json'})
          .subscribe(
            res =>
            {
              if (res.status === 200)
              {
                if (res.body === null){ return; }
                sessionStorage.setItem('x', 'user');
                sessionStorage.setItem('y', '123');
                sessionStorage.setItem('m', res.body.id_user);
                sessionStorage.setItem('token', res.body.token.toString());
              }
            }
          );
    }
    else
    {
      this.http.post<any>(this.apiUrl + 'authenticate', {
            username: 'user',
            password: '123',
          }, {observe: 'response', responseType: 'json'})
          .subscribe(
            res2 =>
            {
              setTimeout(() => {
                if (res2.status === 200)
                {
                if (res2.body === null){ return; }
                this.http.post(this.apiUrl + 'Users/decrypt?pass=' + sessionStorage.getItem('y'), null,
                {
                  observe: 'response',
                  responseType: 'text',
                  headers: new HttpHeaders({
                    Authorization: 'Bearer ' + res2.body.token
                  })
                }).subscribe(
                  res =>
                  {
                      if (res.status === 401)
                      {
                        sessionStorage.setItem('x', 'user');
                        sessionStorage.setItem('y', '123');
                        alert('La sesión ha expirado!');
                        window.location.reload();
                      }
                      this.http.post<any>(this.apiUrl + 'authenticate', {
                        username: sessionStorage.getItem('x'),
                        password: res.body,
                      },
                      {
                        observe: 'response',
                        responseType: 'json'
                      })
                      .subscribe(
                        auth =>
                        {
                          if (res.status === 200)
                          {
                            sessionStorage.setItem('token', auth.body.token);
                          }
                        }
                      );
                  });
              }
              }, 1000);
            });
    }
  }
}
