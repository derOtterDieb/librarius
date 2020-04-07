import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGearLbr } from 'app/shared/model/gear-lbr.model';

@Component({
  selector: 'jhi-gear-lbr-detail',
  templateUrl: './gear-lbr-detail.component.html'
})
export class GearLbrDetailComponent implements OnInit {
  gear: IGearLbr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gear }) => (this.gear = gear));
  }

  previousState(): void {
    window.history.back();
  }
}
