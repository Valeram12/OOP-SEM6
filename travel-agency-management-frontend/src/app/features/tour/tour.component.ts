import { Component } from '@angular/core';
import {tourService} from "../../core/services/tour.service";
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {tourWithcustomerMembers} from "../../shared/models/TourWithcustomerMembers";
import {RelationService} from "../../core/services/relation.service";

@Component({
  selector: 'app-tour',
  templateUrl: './tour.component.html',
  providers: [tourService]
})
export class tourComponent {
  private _tour!: tourWithcustomerMembers;
  private subscription$?: Subscription;

  constructor(private readonly tourService: tourService,
              private readonly relationService: RelationService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.retrievetour();
  }

  private retrievetour(): void {
    this.subscription$ = this.activatedRoute.params.subscribe(params => {
      this.tourService.gettourById(Number(params['id'])).subscribe(response => {
        this._tour = response;
      })
    });
  }

  get tour(): tourWithcustomerMembers {
    return this._tour;
  }

  public routeToEditForm(id: number) {
    this.router.navigate(['tours/edit', id]);
  }

  deletecustomerMemberFromFlight(customerMemberId: number) {
    const confirmDelete = confirm(`Do you want to delete customer member with id: ${customerMemberId}?`);

    if (confirmDelete) {
      this.relationService.deleteRelation(customerMemberId, this._tour.id).subscribe(() => {
        this.retrievetour();
      })
    }
  }

  ngOnDestroy(): void {
  }
}
