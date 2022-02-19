import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ContactComponent} from './index/contact/contact.component';
import {DetailComponent} from './index/detail/detail/detail.component';
import {GenreComponent} from './index/genre/genre.component';
import {HomeComponent} from './index/home/home.component';
import {IndexComponent} from './index/index.component';
import {SearchComponent} from './index/search/search.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {LogoutComponent} from './logout/logout.component';
import {AuthGuardService} from './_services/authGuard.service';

const routes: Routes = [
  {
    path: 'index', component: IndexComponent, children: [
      {path: 'main', component: HomeComponent},
      {path: 'genre', component: GenreComponent},
      {path: 'contact', component: ContactComponent},
      {path: 'detail/:id', component: DetailComponent},
      {path: 'search', component: SearchComponent}
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'register', component: RegisterComponent},
  {path: '**', redirectTo: 'index/main', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
