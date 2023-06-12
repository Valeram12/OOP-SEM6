import {Component, OnDestroy, OnInit} from '@angular/core';
import {customerMemberService} from "../../core/services/customer-member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {customerMember} from "../../shared/models/CustomerMember";

@Component({
  selector: 'app-customer-members',
  templateUrl: './customer.component.html',
  providers: [customerMemberService]
})
export class customerComponent implements OnInit, OnDestroy {
  private _members: customerMember[] = [];

  constructor(private readonly customerMemberService: customerMemberService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.getcustomerMembers();
  }

  private getcustomerMembers(): void {
    this.customerMemberService.getcustomerMembers().subscribe(response => {
      this._members = response.crewMembers;
    })
  }

  get members(): customerMember[] {
    return this._members;
  }

  getcustomerMemberById(id: number) {
    this.router.navigate(['get', id], {
      relativeTo: this.activatedRoute
    });
  }

  deletecustomerMember(id: number) {
    const confirmDelete = confirm(`Do you want to delete crew member with id: ${id}?`);

    if (confirmDelete) {
      this.customerMemberService.delete(id).subscribe(() => {
        this.getcustomerMembers();
      })
    }
  }

  ngOnDestroy(): void {

  }

  showNewcustomerMemberForm() {
    this.router.navigate(['new'], {
      relativeTo: this.activatedRoute
    })
  }
}
