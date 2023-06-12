import { Component } from '@angular/core';
import {customerMember} from "../../shared/models/CustomerMember";
import {customerMemberService} from "../../core/services/customer-member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Position} from "../../shared/enums/Position";

@Component({
  selector: 'app-customer-member-edit-form',
  templateUrl: './customer-member-edit-form.component.html'
})
export class customerMemberEditFormComponent {
  private _customerMember!: customerMember;

  constructor(private readonly customerMemberService: customerMemberService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.initcustomerMember();
  }

  get customerMember(): customerMember {
    return this._customerMember;
  }

  public get positionValues(): string[] {
    return [
      ...Object.values(Position)
    ]
  }

  private initcustomerMember(): void {
    this.activatedRoute.params.subscribe(params => {
      this.customerMemberService.getcustomerMemberById(Number(params['id'])).subscribe(response => {
        this._customerMember = response;
      })
    });
  }

  onSubmit() {
    this.customerMemberService.updatecustomerMember(this._customerMember).subscribe(response => {
      this.router.navigate(['customer-members/get', response.id]);
    })
  }
}
