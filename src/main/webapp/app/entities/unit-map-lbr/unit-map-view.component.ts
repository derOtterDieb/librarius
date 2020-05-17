import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';
import { computeGearPoint } from 'app/shared/util/compute-points-util';

@Component({
  selector: 'jhi-unit-map-view',
  templateUrl: './unit-map-view.component.html'
})
export class UnitMapViewComponent implements OnInit {
  unitMap: IUnitMapLbr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitMap }) => (this.unitMap = unitMap));
  }

  previousState(): void {
    window.history.back();
  }

  public computeGearPoint(unitMap: IUnitMapLbr): number {
    return computeGearPoint(unitMap);
  }
}
