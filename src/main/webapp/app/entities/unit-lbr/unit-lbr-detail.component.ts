import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnitLbr } from 'app/shared/model/unit-lbr.model';

@Component({
  selector: 'jhi-unit-lbr-detail',
  templateUrl: './unit-lbr-detail.component.html'
})
export class UnitLbrDetailComponent implements OnInit {
  unit: IUnitLbr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unit }) => (this.unit = unit));
  }

  previousState(): void {
    window.history.back();
  }
}
