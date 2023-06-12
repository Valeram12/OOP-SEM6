import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RelationService} from "../../core/services/relation.service";
import {customerMemberFlightRelation} from "../../shared/models/CustomerMemberFlightRelation";

@Component({
  selector: 'app-relation-new-form',
  templateUrl: './relation-new-form.component.html'
})
export class RelationNewFormComponent implements OnInit {
  private _relation!: customerMemberFlightRelation;

  constructor(private readonly relationService: RelationService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this._relation = this.initRelation();
  }

  private initRelation(): customerMemberFlightRelation {
    return {
      customerMemberId: 0,
      tourId: 0
    }
  }

  get relation(): customerMemberFlightRelation {
    return this._relation;
  }

  onSubmit() {
    this.relationService.createRelation(this.relation.customerMemberId, this.relation.tourId).subscribe(response => {
      this.router.navigate(['customer-members/get', this.relation.customerMemberId]);
    })
  }
}
