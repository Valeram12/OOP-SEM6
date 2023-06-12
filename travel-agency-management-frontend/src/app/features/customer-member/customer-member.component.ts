import {Component, OnDestroy, OnInit} from '@angular/core';
import {customerMembersWithtour} from "../../shared/models/CustomerMembersWithtour";
import {customerMemberService} from "../../core/services/customer-member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {RelationService} from "../../core/services/relation.service";

@Component({
  selector: 'app-crew-member',
  templateUrl: './customer-member.component.html',
  providers: [customerMemberService]
})
export class customerMemberComponent implements OnInit, OnDestroy {
  private _customerMember!: customerMembersWithtour;
  private subscription$?: Subscription;

  constructor(private readonly customerMemberService: customerMemberService,
              private readonly relationService: RelationService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.retrieveCrewMember();
  }

  private retrieveCrewMember(): void {
    this.subscription$ = this.activatedRoute.params.subscribe(params => {
      this.customerMemberService.getcustomerMemberById(Number(params['id'])).subscribe(response => {
        this._customerMember = response;
      })
    });
  }

  get customerMember(): customerMembersWithtour {
    return this._customerMember;
  }

  public routeToEditForm(id: number) {
    this.router.navigate(['customer-members/edit', id]);
  }

  deleteFlightFromCrewMember(tourId: number) {
    const confirmDelete = confirm(`Do you want to delete tour with id: ${tourId}?`);

    if (confirmDelete) {
      this.relationService.deleteRelation(this._customerMember.id, tourId).subscribe(() => {
        this.retrieveCrewMember();
      })
    }
  }

  ngOnDestroy(): void {
    this.subscription$?.unsubscribe();
  }
}
