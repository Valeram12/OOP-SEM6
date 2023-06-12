import { Component } from '@angular/core';
import {customerMember} from "../../shared/models/CustomerMember";
import {customerMemberService} from "../../core/services/customer-member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Position} from "../../shared/enums/Position";
import {tourService} from "../../core/services/tour.service";
import {tour} from "../../shared/models/Tour";

@Component({
  selector: 'app-tour-edit-form',
  templateUrl: './tour-edit-form.component.html'
})
export class tourEditFormComponent {
  private _tour!: tour;

  constructor(private readonly tourService: tourService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.inittour();
  }

  get tour(): tour {
    return this._tour;
  }

  private inittour(): void {
    this.activatedRoute.params.subscribe(params => {
      this.tourService.gettourById(Number(params['id'])).subscribe(response => {
        this._tour = response;
      })
    });
  }

  onSubmit() {
    this.tourService.updatetour(this._tour).subscribe(response => {
      this.router.navigate(['tour/get', response.id]);
    })
  }
}
