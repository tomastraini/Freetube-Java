import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-editar-perfil',
  templateUrl: './editar-perfil.component.html',
  styleUrls: ['./editar-perfil.component.scss']
})
export class EditarPerfilComponent implements OnInit {
  @Input() busquedavalue: any;
  file: any;
  oldpassword: any;
  passwordnew: any;
  passwordconfirm: any;
  errorTypes = 0;
  userImgisEmpty: any = false;

  constructor(private appComponent: AppComponent, public http: HttpClient) 
  {
    setInterval(() => {
      this.userImgisEmpty = document.getElementById('wizardPicturePreview')?.getAttribute("src")
      if(this.userImgisEmpty)
      {
        clearInterval(this.userImgisEmpty);
      }
    }, 1000);
  }

  userImg = '';
  userImgOnChangedSRC: any;

  ngOnInit(): void
  {
    if (sessionStorage.getItem('m') !== undefined && sessionStorage.getItem('m') !== null)
    {
      this.userImg = this.appComponent.apiUrl + 'Users?id_user=' + sessionStorage.getItem('m');
    }
    else
    {
      window.location.href = '/login';
    }
  }


  clearImage(): void
  {
    this.userImgOnChangedSRC = null;
    this.file = null;
  }

  readURL(event: any): void
  {
    if (event.target.files && event.target.files[0]) {
        this.file = event.target.files[0];

        const reader = new FileReader();
        reader.readAsDataURL(this.file);
        reader.onload = e => this.userImgOnChangedSRC = reader.result;
        console.log(this.userImgOnChangedSRC);
        console.log(this.userImg);
    }
  }


  AbrirSelector(): void
  {
    const input = document.getElementById('wizard-picture');
    input?.click();
  }

  changeImageAlone(): void
  {
    if (this.file !== null && this.file !== undefined)
    {
      const formData = new FormData();
      formData.append('files', this.file);
      this.http.patch(this.appComponent.apiUrl + 'Users/image?id_user=' + sessionStorage.getItem('m'), formData, {
        observe: 'response',
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + sessionStorage.getItem('token')})
          }).subscribe(
            res =>
            {
              if (res.status === 200)
              {
                this.userImg = this.appComponent.apiUrl + 'Users?id_user=' + sessionStorage.getItem('m');
                window.location.href = '/';
              }
            }
          );
    }
    else
    {
      this.http.delete(this.appComponent.apiUrl + 'Users/image?id_user=' + sessionStorage.getItem('m'), {
        observe: 'response',
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + sessionStorage.getItem('token')})
          }).subscribe(
            res =>
            {
              if (res.status === 200)
              {
                this.userImg = this.appComponent.apiUrl + 'Users?id_user=' + sessionStorage.getItem('m');
                window.location.href = '/';
              }
            }
          );

    }
  }

  change(event: any): void
  {


    if (this.oldpassword === undefined || this.oldpassword === null || this.oldpassword === '')
    {
      this.errorTypes = 1;
      return;
    }
    if (this.passwordnew === undefined || this.passwordnew === null || this.passwordnew === '')
    {
      this.errorTypes = 2;
      return;
    }
    if (this.passwordconfirm === undefined || this.passwordconfirm === null || this.passwordconfirm === '')
    {
      this.errorTypes = 3;
      return;
    }
    if (this.passwordnew !== this.passwordconfirm)
    {
      this.errorTypes = 4;
      return;
    }

    this.http.patch(this.appComponent.apiUrl +
      'Users/?id_user=' + sessionStorage.getItem('m') +
      '&oldpassword=' + this.oldpassword + '&password=' + this.passwordnew, null, {
        observe: 'response',
        responseType: 'json',
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + sessionStorage.getItem('token')})
          }).subscribe(
            res =>
            {
              if (res.status === 200)
              {
              sessionStorage.removeItem('m');
              sessionStorage.setItem('x', 'user');
              sessionStorage.setItem('y', '123');
              window.location.href = '/login';
              }
              else
              {
                this.errorTypes = 5;
              }
            },
            err =>
            {
              this.errorTypes = 5;
            }
          );

  }
}
