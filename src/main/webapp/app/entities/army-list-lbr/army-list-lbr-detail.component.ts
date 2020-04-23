import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';
import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { UnitLbrService } from 'app/entities/unit-lbr/unit-lbr.service';
// import { IGearLbr } from 'app/shared/model/gear-lbr.model';
import { GearLbrService } from 'app/entities/gear-lbr/gear-lbr.service';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-army-list-lbr-detail',
  templateUrl: './army-list-lbr-detail.component.html'
})
export class ArmyListLbrDetailComponent implements OnInit {
  public armyList: IArmyListLbr | null = null;
  public addNewUnit = false;
  public newUnit: IUnitLbr;
  public unitList: IUnitLbr[];
  public availableUnit: Observable<any>;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected unitService: UnitLbrService,
    protected gearService: GearLbrService,
    protected armyListService: ArmyListLbrService
  ) {
    this.newUnit = new UnitLbr();
    this.unitList = new Array<UnitLbr>();
    this.availableUnit = new Observable<any>();
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(res => {
      this.armyList = res.armyList;
      this.getAllUnits();
    });
    this.getAllAvailableUnits();
  }

  previousState(): void {
    window.history.back();
  }

  private getAllUnits(): void {
    if (this.armyList != null && this.armyList.unitIds != null) {
      for (const id of this.armyList.unitIds) {
        this.unitService.find(id).subscribe(res => {
          if (res.body != null) {
            this.unitList.push(res.body);
          }
        });
      }
    }
    this.computePoints();
  }

  private getAllAvailableUnits(): void {
    this.availableUnit = this.unitService.query();
  }

  private computePoints(): void {
    if (this.armyList != null && this.armyList.totalPoint != null) {
      this.armyList.totalPoint = 0;
      for (const unit of this.unitList) {
        if (unit != null && unit.totalPoint != null) {
          this.armyList.totalPoint += unit.totalPoint;
        }
      }
    }
  }

  public saveUnit(unit: IUnitLbr): void {
    if (this.armyList != null) {
      this.armyListService.addUnit(this.armyList, unit).subscribe(res => (this.armyList = res.body));
    }
  }
}
