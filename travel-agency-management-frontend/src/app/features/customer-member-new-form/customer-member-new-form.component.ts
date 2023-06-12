import {Component, OnInit} from '@angular/core';
import {customerMember} from "../../shared/models/CustomerMember";
import {customerMemberService} from "../../core/services/customer-member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Position} from "../../shared/enums/Position";

@Component({
  selector: 'app-customer-member-new-form',
  templateUrl: './customer-member-new-form.component.html'
})
export class customerMemberNewFormComponent implements OnInit {
  private _customerMember!: customerMember;

  constructor(private readonly customerMemberService: customerMemberService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this._customerMember = this.initEmptycustomerMember();
  }

  get customerMember(): customerMember {
    return this._customerMember;
  }

  public get positionValues(): string[] {
    return [
      ...Object.values(Position)
    ]
  }

  private initEmptycustomerMember(): customerMember {
    return {
      id: 0,
      name: "",
      surname: "",
      position: Position.pilot
    };
  }

  onSubmit() {
    this.customerMemberService.createcustomerMember(this._customerMember).subscribe(response => {
      this.router.navigate(['customer-members/get', response.id]);
    })
  }
}
