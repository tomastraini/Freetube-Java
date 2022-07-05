import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(public router: Router, public http: HttpClient, private appComponent: AppComponent) { }

  imageSrc: any;
  file: any = null;

  errorTypes = 0;
  loading = false;


  ngOnInit(): void
  {

  }

  login(): void
  {
    this.router.navigate(['/login']);
  }

  register(event: any): void
  {


    if (event.user.value === null || event.user.value === '' || event.user.value === undefined)
    {
      this.errorTypes = 1;
      return;
    }

    if (event.password.value === null || event.password.value === '' || event.password.value === undefined)
    {
      this.errorTypes = 2;
      return;
    }

    if (event.correo.value === null || event.correo.value === '' || event.correo.value === undefined)
    {
      this.errorTypes = 3;
      return;
    }

    if (event.nombreyapellido.value === null || event.nombreyapellido.value === '' || event.nombreyapellido.value === undefined)
    {
      this.errorTypes = 4;
      return;
    }

    if (event.telefono.value === null || event.telefono.value === '' || event.telefono.value === undefined)
    {
      this.errorTypes = 5;
      return;
    }

    if (!(event.correo.value.includes('@') && event.correo.value.includes('.')))
    {
      this.errorTypes = 6;
      return;
    }
    this.loading = true;
    const formData = new FormData();
    formData.append('files', this.file);
    console.log(this.file);
    
    if (this.file !== null)
    {
      this.http.post(this.appComponent.apiUrl + 'Users?' +
      'username=' + event.user.value + '&password=' + event.password.value + '&correo=' + event.correo.value + '&' +
      'nombreyapellido=' + event.nombreyapellido.value + '&telefono=' + event.telefono.value,
      formData,
      {
        observe: 'response',
        responseType: 'json',
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + sessionStorage.getItem('token')
        },
        )
        }).subscribe(
        (data: any) => {
  
          this.router.navigate(['/']);
        });
    }
    else
    {
      this.http.post(this.appComponent.apiUrl + 'Users/withOutImage?' +
      'username=' + event.user.value + '&password=' + event.password.value + '&correo=' + event.correo.value + '&' +
      'nombreyapellido=' + event.nombreyapellido.value + '&telefono=' + event.telefono.value,
      null,
      {
        observe: 'response',
        responseType: 'json',
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + sessionStorage.getItem('token')
        },
        )
        }).subscribe(
        (data: any) => {
          this.http.post(this.appComponent.apiUrl + 'Users/encrypt?pass=' + event.password.value, null, {
            observe: 'response',
            responseType: 'text',
            headers: new HttpHeaders({
              Authorization: 'Bearer ' + sessionStorage.getItem('token')
            },
            )
            }).subscribe(
            (dataEncrypt: any) => {
              sessionStorage.setItem('x', data.body.usuario);
              sessionStorage.setItem('y', dataEncrypt.body);
              sessionStorage.setItem('m', data.body.id_usuario)
              this.router.navigate(['/']);
            });
        });

    }

  }

  readURL(event: any): void
  {
    if (event.target.files && event.target.files[0]) {
        this.file = event.target.files[0];

        const reader = new FileReader();
        reader.onload = e => this.imageSrc = reader.result;

        reader.readAsDataURL(this.file);
    }
  }

  goAbout(): void
  {
    this.router.navigate(['/about']);
  }
}
