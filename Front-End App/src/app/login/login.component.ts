import { Component, OnInit } from '@angular/core';
import { KeycloakService} from 'keycloak-angular';
import {KeycloakProfile} from 'keycloak-js';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public isLoggedIn = false;
  public userProfile: KeycloakProfile | null = null;

  constructor(private readonly keycloak: KeycloakService,
              private router: Router) { }

  public async ngOnInit()  {
    this.isLoggedIn = await this.keycloak.isLoggedIn();
    if (this.isLoggedIn) {
      this.router.navigate(['/index/main']);
    }else{
      this.login();
    }
  }

  public login(): void {
    this.keycloak.login();
  }

}
