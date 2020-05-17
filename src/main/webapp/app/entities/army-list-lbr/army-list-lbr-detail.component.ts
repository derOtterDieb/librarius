import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';
import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { UnitLbrService } from 'app/entities/unit-lbr/unit-lbr.service';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';
import { Observable } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ArmyListLbrAssociateUnitDialogComponent } from 'app/entities/army-list-lbr/army-list-lbr-associate-unit-dialog.component';
import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';
import { IGearLbr } from 'app/shared/model/gear-lbr.model';
import { AccountService } from 'app/core/auth/account.service';
import { ISquadronMapLbr, SquadronMapLbr } from 'app/shared/model/squadron-map-lbr.model';

@Component({
  selector: 'jhi-army-list-lbr-detail',
  templateUrl: './army-list-lbr-detail.component.html'
})
export class ArmyListLbrDetailComponent implements OnInit {
  public armyList: IArmyListLbr | null = null;
  public addNewUnit = false;
  public newUnit: IUnitLbr;
  public availableUnit: Observable<any>;
  public gearSearch: string;
  public gearList: IGearLbr[];
  public unitSearch: string;
  public squadronView: boolean;
  public squadrons: ISquadronMapLbr[];
  public userId: any;
  public armyListId: string;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected unitService: UnitLbrService,
    protected armyListService: ArmyListLbrService,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {
    this.newUnit = new UnitLbr();
    this.availableUnit = new Observable<any>();
    this.gearSearch = '';
    this.gearList = new Array<IGearLbr>();
    this.unitSearch = '';
    this.squadronView = false;
    this.squadrons = new Array<SquadronMapLbr>();
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
            this.sortArmyListUnitMaps();
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

  public searchUnit(): void {
    if (this.unitSearch !== '' && this.unitSearch != null) {
      this.availableUnit = this.unitService.getAllByName(this.unitSearch);
    } else {
      this.availableUnit = this.unitService.query();
    }
  }

  public shouldReloadUnitList(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.armyListService.find(params.get('id')).subscribe(
        res => (this.armyList = res.body),
        () => {},
        () => this.sortArmyListUnitMaps()
      );
    });
  }

  private sortArmyListUnitMaps(): void {
    if (this.armyList && this.armyList.unitMaps && this.armyList.id) {
      this.armyListId = this.armyList.id;
      this.armyList.unitMaps.sort((a: IUnitMapLbr, b: IUnitMapLbr) => {
        if (a.unit && a.unit.unitName && b.unit && b.unit.unitName) {
          return a.unit?.unitName?.toUpperCase() > b.unit?.unitName?.toUpperCase() ? 1 : -1;
        } else {
          return -1;
        }
      });
    }
  }
}
