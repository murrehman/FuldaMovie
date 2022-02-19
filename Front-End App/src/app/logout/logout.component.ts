import { Component, OnInit } from '@angular/core';
import { KeycloakService} from 'keycloak-angular';
import {KeycloakProfile} from 'keycloak-js';
import {Router} from '@angular/router';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {
  public isLoggedIn = false;
  public userProfile: KeycloakProfile | null = null;
  private frontendUrl: string = environment.frontendUrl;

  constructor(private readonly keycloak: KeycloakService,
              private router: Router) { }

  public ngOnInit(): void  {
      this.logout();
  }

  public logout(): void {
    this.keycloak.logout(this.frontendUrl + 'index/main');
  }


}
