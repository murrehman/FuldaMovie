import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './index/header/header.component';
import { HomeComponent } from './index/home/home.component';
import { IndexComponent } from './index/index.component';
import { FooterComponent } from './index/footer/footer.component';
import { GenreComponent } from './index/genre/genre.component';
import { LoginComponent } from './login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { DetailComponent } from './index/detail/detail/detail.component';
import { MostWaitedMoviesComponent } from './index/most-waited-movies/most-waited-movies.component';
import { FormsModule } from '@angular/forms';
import { SearchComponent } from './index/search/search.component';

import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { RegisterComponent } from './register/register.component';
import { UserComponent } from './index/header/user/user.component';
import { LogoutComponent } from './logout/logout.component';
import {environment} from '../environments/environment';

function initializeKeycloak(keycloak: KeycloakService): any {
  return () =>
    keycloak.init({
      config: environment.keycloakConfig,
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      }
    });
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    IndexComponent,
    FooterComponent,
    GenreComponent,
    LoginComponent,
    DetailComponent,
    MostWaitedMoviesComponent,
    SearchComponent,
    RegisterComponent,
    UserComponent,
    LogoutComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    HttpClientModule,
    AppRoutingModule,
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
