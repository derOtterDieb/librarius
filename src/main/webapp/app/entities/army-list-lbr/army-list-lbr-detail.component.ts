import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';
import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { UnitLbrService } from 'app/entities/unit-lbr/unit-lbr.service';
// import { IGearLbr } from 'app/shared/model/gear-lbr.model';
import { GearLbrService } from 'app/entities/gear-lbr/gear-lbr.service';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';
import { Observable } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ArmyListLbrAssociateUnitDialogComponent } from 'app/entities/army-list-lbr/army-list-lbr-associate-unit-dialog.component';
import { IUnitMapLbr, UnitMapLBr } from 'app/shared/model/unit-map-lbr.model';
import { GearLbr, IGearLbr } from 'app/shared/model/gear-lbr.model';
import { UnitMapLbrService } from 'app/entities/unit-map-lbr/unit-map-lbr.service';

@Component({
  selector: 'jhi-army-list-lbr-detail',
  templateUrl: './army-list-lbr-detail.component.html'
})
export class ArmyListLbrDetailComponent implements OnInit {
  public armyList: IArmyListLbr | null = null;
  public addNewUnit = false;
  public newUnit: IUnitLbr;
  public availableUnit: Observable<any>;
  public toggledGear: string;
  public gearSearch: string;
  public gearList: IGearLbr[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected unitService: UnitLbrService,
    protected gearService: GearLbrService,
    protected armyListService: ArmyListLbrService,
    protected unitMapLbrService: UnitMapLbrService,
    protected modalService: NgbModal
  ) {
    this.newUnit = new UnitLbr();
    this.availableUnit = new Observable<any>();
    this.toggledGear = '';
    this.gearSearch = '';
    this.gearList = new Array<IGearLbr>();
  }

  ngOnInit(): void {
    this.getAllUnits();
    this.getAllAvailableUnits();
  }

  previousState(): void {
    window.history.back();
  }

  private getAllUnits(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.armyListService.find(params.get('id')).subscribe(
        res => (this.armyList = res.body),
        () => {},
        () => {
          if (this.armyList && this.armyList.unitMaps) {
            this.armyList.unitMaps.sort((a: IUnitMapLbr, b: IUnitMapLbr) => {
              if (a.unit && a.unit.unitName && b.unit && b.unit.unitName) {
                return a.unit?.unitName?.toUpperCase() > b.unit?.unitName?.toUpperCase() ? 1 : -1;
              } else {
                return -1;
              }
            });
          }
        }
      );
    });
  }

  private getAllAvailableUnits(): void {
    this.availableUnit = this.unitService.query();
  }

  public associate(unit: IUnitLbr): void {
    const modalRef = this.modalService.open(ArmyListLbrAssociateUnitDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.armyList = this.armyList;
    modalRef.componentInstance.unit = unit;
    modalRef.result.then(() => this.getAllUnits());
  }

  public deleteUnit(unit: IUnitMapLbr): void {
    if (this.armyList) {
      this.armyListService.removeUnit(this.armyList, unit).subscribe(() => this.ngOnInit());
    }
  }

  public toggleGear(unitMap: IUnitMapLbr): void {
    if (this.toggledGear !== unitMap.id) {
      this.toggledGear = unitMap.id ? unitMap.id : '';
    } else {
      this.toggledGear = '';
    }
  }

  public searchGear(): void {
    if (this.gearSearch !== '' && this.gearSearch != null) {
      this.gearService.findAllByName(this.gearSearch).subscribe(res => (this.gearList = res.body ? res.body : new Array<GearLbr>()));
    } else {
      this.gearService.query().subscribe(res => (this.gearList = res.body ? res.body : new Array<GearLbr>()));
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
      this.unitMapLbrService.update(unitMap).subscribe(() => this.getAllUnits());
    }
  }

  public removeGearFromUnit(gear: IGearLbr, unitMap: UnitMapLBr): void {
    if (unitMap && unitMap.gears) {
      unitMap.gears.splice(unitMap.gears.indexOf(gear), 1);
      this.unitMapLbrService.update(unitMap).subscribe(() => this.getAllUnits());
    }
  }

  public computeGearPoint(unitMap: IUnitMapLbr): number {
    if (unitMap && unitMap.gears) {
      let result = 0;
      for (const gear of unitMap.gears) {
        result += gear.pointValue ? gear.pointValue : 0;
      }
      return result;
    } else {
      return 0;
    }
  }
}
