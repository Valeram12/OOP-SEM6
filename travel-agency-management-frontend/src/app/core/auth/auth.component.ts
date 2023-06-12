import {Component, Injectable, OnInit} from '@angular/core';
import {AuthService} from "@auth0/auth0-angular";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html'
})
@Injectable({
  providedIn: "root"
})
export class AuthComponent implements OnInit{
  private _role: string | undefined;

  constructor(private auth: AuthService,
              private router: Router) {}

  ngOnInit(): void {
    this.login();
  }

  private login(): void {
    this.auth.loginWithRedirect({
      appState: {
        target: '/profile',
      },
    });
  }

  get role(): string | undefined {
    this.auth.idTokenClaims$.subscribe(idToken => {
      this._role = idToken?.nickname;
    })

    return this._role;
  }

  public logout(): void {
    this.auth.logout();
  }

  public isAuthenticated(): Observable<boolean> {
    return this.auth.isAuthenticated$;
  }

  public getUser(): Observable<import("@auth0/auth0-spa-js").User | null | undefined> {
    return this.auth.user$;
  }
}
