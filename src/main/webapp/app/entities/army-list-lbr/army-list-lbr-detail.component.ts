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
import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';

@Component({
  selector: 'jhi-army-list-lbr-detail',
  templateUrl: './army-list-lbr-detail.component.html'
})
export class ArmyListLbrDetailComponent implements OnInit {
  public armyList: IArmyListLbr | null = null;
  public addNewUnit = false;
  public newUnit: IUnitLbr;
  public availableUnit: Observable<any>;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected unitService: UnitLbrService,
    protected gearService: GearLbrService,
    protected armyListService: ArmyListLbrService,
    protected modalService: NgbModal
  ) {
    this.newUnit = new UnitLbr();
    this.availableUnit = new Observable<any>();
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
      this.armyListService.find(params.get('id')).subscribe(res => {
        this.armyList = res.body;
      });
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
}
