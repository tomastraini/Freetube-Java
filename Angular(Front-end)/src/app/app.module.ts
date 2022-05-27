import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './searchvideos/menu/menu.component';
import { FormsModule } from '@angular/forms';
import { routingcomponents } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import {MatGridListModule} from '@angular/material/grid-list';
import { VideoWatchComponent } from './searchvideos/video-watch/video-watch.component';
import { TableFilterPipe } from './Pipes/mypipe';
import { SearchvideosComponent } from './searchvideos/searchvideos.component';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { PagnotfoundComponent } from './pagnotfound/pagnotfound.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { SecurePipe } from './Pipes/SecurePipe';
import {MatSelectModule} from '@angular/material/select';
import { AboutComponent } from './searchvideos/about/about.component';
import { ProfileComponent } from './searchvideos/profile/profile.component';
import { EditarPerfilComponent } from './searchvideos/editar-perfil/editar-perfil.component';

@NgModule({
  declarations: [
    AppComponent,
    routingcomponents,
    TableFilterPipe,
    SecurePipe,
    MenuComponent,
    VideoWatchComponent,
    SearchvideosComponent,
    PagnotfoundComponent,
    LoginComponent,
    RegisterComponent,
    AboutComponent,
    ProfileComponent,
    EditarPerfilComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatPaginatorModule,
    HttpClientModule,
    FormsModule,
    MatGridListModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
