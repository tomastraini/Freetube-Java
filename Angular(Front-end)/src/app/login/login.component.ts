import { HttpClient, HttpErrorResponse, HttpHeaders, } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(public http: HttpClient, public router: Router, private appComponent: AppComponent) {  }

  user: any;
  password: any;
  errorTypes = 0;
  loading = false;

  ngOnInit(): void
  {
  }

  login(event: any): void
  {
    if (this.user === 'user'){ return; }
    this.loading = true;
    this.http.post<any>(this.appComponent.apiUrl + 'authenticate',
    {
      username: this.user,
      password: this.password
    },
    {
      observe: 'response',
      responseType: 'json',
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + sessionStorage.getItem('token')
      })
    }).subscribe(
      res =>
      {
        if (res.status === 200 && res.body !== null)
        {
          this.http.post(this.appComponent.apiUrl + 'Users/encrypt?pass=' + this.password, null,
          {
            observe: 'response',
            responseType: 'text',
            headers: new HttpHeaders({
              Authorization: 'Bearer ' + sessionStorage.getItem('token')
            })
          }).subscribe(
            res2 =>
            {
              sessionStorage.setItem('x', this.user);
              sessionStorage.setItem('y', res2.body != null ? res2.body : '');
              sessionStorage.setItem('m', res.body.id_user);
              sessionStorage.setItem('token', res.body.token);
              window.location.href = '/';
            });
        }
        else
        {
          this.errorTypes = 1;
          this.loading = false;
        }
      },
      err =>
      {
        this.errorTypes = 1;
        this.loading = false;
      }
    );
  }
  register(): void
  {
    this.router.navigate(['/register']);
  }
  goAbout(): void
  {
    this.router.navigate(['/about']);
  }
}


