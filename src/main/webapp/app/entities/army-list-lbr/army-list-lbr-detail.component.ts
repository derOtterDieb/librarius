import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';
import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { UnitLbrService } from 'app/entities/unit-lbr/unit-lbr.service';
import { GearLbrService } from 'app/entities/gear-lbr/gear-lbr.service';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';
import { Observable } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ArmyListLbrAssociateUnitDialogComponent } from 'app/entities/army-list-lbr/army-list-lbr-associate-unit-dialog.component';
import { IUnitMapLbr, UnitMapLBr } from 'app/shared/model/unit-map-lbr.model';
import { GearLbr, IGearLbr } from 'app/shared/model/gear-lbr.model';
import { UnitMapLbrService } from 'app/entities/unit-map-lbr/unit-map-lbr.service';
import { SquadronLbrService } from 'app/entities/squadron-lbr/squadron-lbr.service';
import { SquadronLbr } from 'app/shared/model/squadron-lbr.model';
import { AccountService } from 'app/core/auth/account.service';
import { SquadronDialogComponent } from 'app/entities/army-list-lbr/squadron-dialog.component';

export interface ISquadronMap {
  squadronId: string;
  unitMaps: IUnitMapLbr[];
  name: string;
}

export class SquadronMap implements ISquadronMap {
  constructor(public squadronId: string, public unitMaps: IUnitMapLbr[], public name: string) {}
}

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
  public unitSearch: string;
  public squadronView: boolean;
  public unitWithSquadron: IUnitMapLbr[];
  public unitWithoutSquadron: IUnitMapLbr[];
  public squadrons: ISquadronMap[];
  public newSquadronName: string;
  public isCreatingSquadron = false;
  public userId: any;
  public armyListId: string;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected unitService: UnitLbrService,
    protected gearService: GearLbrService,
    protected armyListService: ArmyListLbrService,
    protected unitMapLbrService: UnitMapLbrService,
    protected modalService: NgbModal,
    protected squadronLbrService: SquadronLbrService,
    private accountService: AccountService
  ) {
    this.newUnit = new UnitLbr();
    this.availableUnit = new Observable<any>();
    this.toggledGear = '';
    this.gearSearch = '';
    this.gearList = new Array<IGearLbr>();
    this.unitSearch = '';
    this.squadronView = false;
    this.unitWithSquadron = new Array<IUnitMapLbr>();
    this.unitWithoutSquadron = new Array<IUnitMapLbr>();
    this.squadrons = new Array<SquadronMap>();
    this.newSquadronName = '';
    this.userId = '';
    this.armyListId = '';
  }

  ngOnInit(): void {
    this.getAllUnits();
    this.getAllAvailableUnits();
    this.accountService.identity().subscribe(res => (this.userId = res?.id));
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
          if (this.armyList && this.armyList.unitMaps && this.armyList.id) {
            this.armyListId = this.armyList.id;
            this.armyList.unitMaps.sort((a: IUnitMapLbr, b: IUnitMapLbr) => {
              if (a.unit && a.unit.unitName && b.unit && b.unit.unitName) {
                return a.unit?.unitName?.toUpperCase() > b.unit?.unitName?.toUpperCase() ? 1 : -1;
              } else {
                return -1;
              }
            });
            for (const unitMap of this.armyList.unitMaps) {
              if (unitMap.squadronId !== null) {
                this.unitWithSquadron.push(unitMap);
              } else {
                this.unitWithoutSquadron.push(unitMap);
              }
            }
            if (this.unitWithSquadron.length > 0) {
              this.computeSquadrons();
            }
          }
        }
      );
    });
  }

  private getAllAvailableUnits(): void {
    this.availableUnit = this.unitService.query();
  }

  private computeSquadrons(): void {
    const sortedBySquadron = this.unitWithSquadron.sort((a: IUnitMapLbr, b: IUnitMapLbr) => {
      if (a.squadronId && b.squadronId) {
        return a.squadronId > b.squadronId ? 1 : -1;
      } else {
        return -1;
      }
    });
    for (const [index, unitMap] of sortedBySquadron.entries()) {
      if (unitMap.squadronId != null) {
        if (index === 0) {
          const unitMapArray = new Array<UnitMapLBr>();
          unitMapArray.push(unitMap);
          const squadronMap = new SquadronMap(unitMap.squadronId, unitMapArray, '');
          this.squadrons.push(squadronMap);
        } else {
          for (const squadronMap of this.squadrons) {
            if (squadronMap.squadronId === unitMap.squadronId) {
              squadronMap.unitMaps.push(unitMap);
            } else {
              const newUnitMapArray = new Array<UnitMapLBr>();
              newUnitMapArray.push(unitMap);
              const newSquadronMap = new SquadronMap(unitMap.squadronId, newUnitMapArray, '');
              this.squadrons.push(newSquadronMap);
            }
          }
        }
      }
    }
    this.squadrons.forEach(squad => {
      this.squadronLbrService.find(squad.squadronId).subscribe(res => (squad.name = res.body ? (res.body.name ? res.body.name : '') : ''));
    });
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

  public searchUnit(): void {
    if (this.unitSearch !== '' && this.unitSearch != null) {
      this.availableUnit = this.unitService.getAllByName(this.unitSearch);
    } else {
      this.availableUnit = this.unitService.query();
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

  public createSquadron(): void {
    const squadron = new SquadronLbr();
    squadron.userId = this.userId;
    squadron.listId = this.armyListId;
    squadron.name = this.newSquadronName;
    this.squadronLbrService.create(squadron).subscribe(
      () => {},
      () => {},
      () => {
        this.newSquadronName = '';
        this.isCreatingSquadron = false;
        this.getAllUnits();
      }
    );
  }

  public addUnitMapToSquadron(unitMap: IUnitMapLbr): void {
    const modalRef = this.modalService.open(SquadronDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unitMap = unitMap;
    modalRef.componentInstance.userId = this.userId;
    modalRef.componentInstance.listId = this.armyList?.id;

    modalRef.result.then(() => this.getAllUnits());
  }
}
