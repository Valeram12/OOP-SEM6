import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {tour} from "../../shared/models/Tour";
import {tourService} from "../../core/services/tour.service";

@Component({
  selector: 'app-tour-new-form',
  templateUrl: './tour-new-form.component.html'
})
export class tourNewFormComponent implements OnInit{
  private _tour!: tour;

  constructor(private readonly tourService: tourService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this._tour = this.initEmptytour();
  }

  get tour(): tour {
    return this._tour;
  }

  private initEmptytour(): tour {
    return {
      id: 0,
      departureFrom: "",
      destination: "",
      departureTime: new Date(),
      arrivalTime: new Date()
    };
  }

  onSubmit() {
    this.tourService.createtour(this._tour).subscribe(response => {
      this.router.navigate(['tour/get', response.id]);
    })
  }
}
