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

  constructor(private appComponent: AppComponent, public http: HttpClient) { }

  userImg = '';

  ngOnInit(): void {
    if (sessionStorage.getItem('m') !== undefined && sessionStorage.getItem('m') !== null)
    {
      this.userImg = this.appComponent.apiUrl + 'Users?id_user=' + sessionStorage.getItem('m');
    }
    else
    {
      window.location.href = '/login';
    }
  }

  readURL(event: any): void
  {
    if (event.target.files && event.target.files[0]) {
        this.file = event.target.files[0];

        const reader = new FileReader();
        reader.readAsDataURL(this.file);
    }
  }


  AbrirSelector(): void
  {
    const input = document.getElementById('wizard-picture');
    input?.click();
  }

  change(event: any): void
  {
    if (this.file !== null && this.file !== undefined)
    {
      const formData = new FormData();
      formData.append('files', this.file);
      this.http.patch(this.appComponent.apiUrl + 'Users/Image?id_user=' + sessionStorage.getItem('m'), formData, {
        observe: 'response',
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + sessionStorage.getItem('token')})
          }).subscribe(
            res =>
            {
              if (res.status === 200)
              {
                this.userImg = this.appComponent.apiUrl + 'Users?id_user=' + sessionStorage.getItem('m');
                window.location.href = '/perfil';
              }
            }
          );
    }

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
      '&oldpass=' + this.oldpassword + '&newpass=' + this.passwordnew, null, {
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
