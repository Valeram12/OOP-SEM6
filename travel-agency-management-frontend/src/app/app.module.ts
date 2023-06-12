import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NavbarComponent } from './core/components/navbar/navbar.component';
import { customerComponent } from './features/customer-members/customer.component';
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import { tourComponent } from './features/tours/tour.component';
import { customerMemberComponent } from './features/customer-member/customer-member.component';
import { customerMemberNewFormComponent } from './features/customer-member-new-form/customer-member-new-form.component';
import { FormsModule } from "@angular/forms";
import { tourComponent } from './features/tour/tour.component';
import { tourNewFormComponent } from './features/tour-new-form/tour-new-form.component';
import { customerMemberEditFormComponent } from './features/customer-member-edit-form/customer-member-edit-form.component';
import { tourEditFormComponent } from './features/tour-edit-form/tour-edit-form.component';
import { RelationNewFormComponent } from './features/relation-new-form/relation-new-form.component';
import { HomePageComponent } from './core/components/home-page/home-page.component';
import {AuthModule} from "@auth0/auth0-angular";
import { AuthComponent } from './core/auth/auth.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    customerComponent,
    tourComponent,
    customerMemberComponent,
    customerMemberNewFormComponent,
    tourComponent,
    tourNewFormComponent,
    customerMemberEditFormComponent,
    tourEditFormComponent,
    RelationNewFormComponent,
    HomePageComponent,
    AuthComponent
  ],
  imports: [
    BrowserModule,
    RouterOutlet,
    RouterLink,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    AuthModule.forRoot({
      domain: "dev-8ds78nkj2ix1fsry.us.auth0.com",
      clientId: "RbW04z9HED7n4BzMlRAusiw4QLj9q60W",
      authorizationParams: {
        redirect_uri: 'http://localhost:4200/home',
      }
    })
  ],
  providers: [
    customerComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
