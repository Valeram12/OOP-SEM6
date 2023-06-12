import { Component } from '@angular/core';
import {AuthComponent} from "../../auth/auth.component";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {
  constructor(public authComponent: AuthComponent) {
  }
}
