import {Component, OnDestroy, OnInit} from '@angular/core';
import {tour} from "../../shared/models/Tour";
import {customerMemberService} from "../../core/services/customer-member.service";
import {ActivatedRoute, Router} from "@angular/router";
import {tourService} from "../../core/services/tour.service";
import {AuthComponent} from "../../core/auth/auth.component";

@Component({
  selector: 'app-tour',
  templateUrl: './tour.component.html',
  providers: [customerMemberService]
})
export class tourComponent implements OnInit, OnDestroy {
  private _tours: tour[] = [];

  constructor(private readonly tourService: tourService,
              public auth: AuthComponent,
              private router: Router,
              private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.getAlltours();
  }

  private getAlltours(): void {
    this.tourService.getAlltours().subscribe(response => {
      this._tours = response.flights;
    })
  }

  get tours(): tour[] {
    return this._tours;
  }

  gettourById(id: number): void {
    this.router.navigate(['get', id], {
      relativeTo: this.activatedRoute
    });
  }

  deleteFlight(id: number): void {
    const confirmDelete = confirm(`Do you want to delete tour with id: ${id}?`);

    if (confirmDelete) {
      this.tourService.delete(id).subscribe(() => {
        this.getAlltours();
      });
    }
  }

  showNewtourForm() {
    this.router.navigate(['new'], {
      relativeTo: this.activatedRoute
    })
  }

  ngOnDestroy(): void {
  }
}
