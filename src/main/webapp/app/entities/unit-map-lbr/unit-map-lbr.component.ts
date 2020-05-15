import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { IUnitMapLbr, UnitMapLBr } from 'app/shared/model/unit-map-lbr.model';
import { computeGearPoint } from 'app/shared/util/compute-points-util';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';
import { GearLbr, IGearLbr } from 'app/shared/model/gear-lbr.model';
import { GearLbrService } from 'app/entities/gear-lbr/gear-lbr.service';
import { UnitMapLbrService } from 'app/entities/unit-map-lbr/unit-map-lbr.service';

@Component({
  selector: 'jhi-unit-map-lbr',
  templateUrl: './unit-map-lbr.component.html'
})
export class UnitMapLbrComponent implements OnInit {
  @Input() unitMaps!: IUnitMapLbr[];
  @Input() armyListId!: string;
  @Output() emit: EventEmitter<any>;
  public toggledGear: string;
  public gearList: IGearLbr[];
  public gearSearch: string;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected armyListLbrService: ArmyListLbrService,
    protected gearService: GearLbrService,
    protected unitMapLbrService: UnitMapLbrService
  ) {
    this.toggledGear = '';
    this.gearList = new Array<IGearLbr>();
    this.gearSearch = '';
    this.emit = new EventEmitter<any>();
  }

  ngOnInit(): void {}

  public computeGearPoint(unitMap: IUnitMapLbr): number {
    return computeGearPoint(unitMap);
  }

  public toggleGear(unitMap: IUnitMapLbr): void {
    if (this.toggledGear !== unitMap.id) {
      this.toggledGear = unitMap.id ? unitMap.id : '';
    } else {
      this.toggledGear = '';
    }
  }

  public deleteUnit(unit: IUnitMapLbr): void {
    if (this.armyListId) {
      this.armyListLbrService.removeUnit(this.armyListId, unit).subscribe(() => this.ngOnInit());
    }
  }

  public addGearToUnit(gear: IGearLbr, unitMap: UnitMapLBr): void {
    if (unitMap) {
      if (unitMap.gears) {
        unitMap.gears.push(gear);
      } else {
        unitMap.gears = new Array<GearLbr>();
        unitMap.gears.push(gear);
      }
      this.unitMapLbrService.update(unitMap).subscribe(() => this.emit.emit());
    }
  }

  public removeGearFromUnit(gear: IGearLbr, unitMap: UnitMapLBr): void {
    if (unitMap && unitMap.gears) {
      unitMap.gears.splice(unitMap.gears.indexOf(gear), 1);
      this.unitMapLbrService.update(unitMap).subscribe(() => this.emit.emit());
    }
  }

  public searchGear(): void {
    if (this.gearSearch !== '' && this.gearSearch != null) {
      this.gearService.findAllByName(this.gearSearch).subscribe(
        res => (this.gearList = res.body ? res.body : new Array<GearLbr>()),
        () => {},
        () => {
          this.gearList.sort((a: IGearLbr, b: IGearLbr) => {
            if (a && a.gearName && b && b.gearName) {
              return a.gearName.toUpperCase() > b.gearName.toUpperCase() ? 1 : -1;
            } else {
              return -1;
            }
          });
        }
      );
    } else {
      this.gearService.query().subscribe(
        res => (this.gearList = res.body ? res.body : new Array<GearLbr>()),
        () => {},
        () => {
          this.gearList.sort((a: IGearLbr, b: IGearLbr) => {
            if (a && a.gearName && b && b.gearName) {
              return a.gearName.toUpperCase() > b.gearName.toUpperCase() ? 1 : -1;
            } else {
              return -1;
            }
          });
        }
      );
    }
  }
}
