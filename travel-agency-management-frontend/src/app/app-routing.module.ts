import {RouterModule, Routes} from "@angular/router";
import {customerComponent} from "./features/customer-members/customer.component";
import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {tourComponent} from "./features/tours/tour.component";
import {customerMemberComponent} from "./features/customer-member/customer-member.component";
import {
  customerMemberNewFormComponent
} from "./features/customer-member-new-form/customer-member-new-form.component";
import {tourComponent} from "./features/tour/tour.component";
import {tourNewFormComponent} from "./features/tour-new-form/tour-new-form.component";
import {
  customerMemberEditFormComponent
} from "./features/customer-member-edit-form/customer-member-edit-form.component";
import {tourEditFormComponent} from "./features/tour-edit-form/tour-edit-form.component";
import {RelationNewFormComponent} from "./features/relation-new-form/relation-new-form.component";
import {HomePageComponent} from "./core/components/home-page/home-page.component";
import {AuthComponent} from "./core/auth/auth.component";

const routes: Routes = [
  {
    path: "",
    component: AppComponent
  },
  {
    title: 'customer members',
    path: 'customer-members',
    component: customerComponent
  },
  {
    title: 'customer member',
    path: 'customer-members/get/:id',
    component: customerMemberComponent
  },
  {
    title: 'Create new customer member',
    path: 'customer-members/new',
    component: customerMemberNewFormComponent
  },
  {
    title: 'Edit customer member',
    path: 'customer-members/edit/:id',
    component: customerMemberEditFormComponent
  },
  {
    title: 'tour',
    path: 'tour',
    component: tourComponent
  },
  {
    title: 'tour',
    path: 'tour/get/:id',
    component: tourComponent
  },
  {
    title: 'Edit tour',
    path: 'tour/edit/:id',
    component: tourEditFormComponent
  },
  {
    title: 'Create new tour',
    path: 'tour/new',
    component: tourNewFormComponent
  },
  {
    title: 'Add tour to customer member',
    path: 'relation/new',
    component: RelationNewFormComponent
  },
  {
    title: 'Home page',
    path: 'home',
    component: HomePageComponent
  },
  {
    title: 'Auth',
    path: 'auth',
    component: AuthComponent
  },
  {
    path: '',
    redirectTo: 'auth',
    pathMatch: "full"
  }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {

}
