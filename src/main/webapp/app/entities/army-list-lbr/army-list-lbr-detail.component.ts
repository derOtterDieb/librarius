import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';
import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { FormBuilder, Validators } from '@angular/forms';
import { UnitLbrService } from 'app/entities/unit-lbr/unit-lbr.service';
// import { IGearLbr } from 'app/shared/model/gear-lbr.model';
import { GearLbrService } from 'app/entities/gear-lbr/gear-lbr.service';
import { ArmyListLbrService } from 'app/entities/army-list-lbr/army-list-lbr.service';

@Component({
  selector: 'jhi-army-list-lbr-detail',
  templateUrl: './army-list-lbr-detail.component.html'
})
export class ArmyListLbrDetailComponent implements OnInit {
  armyList: IArmyListLbr | null = null;
  addNewUnit = false;
  newUnit: IUnitLbr;
  unitList: IUnitLbr[];
  editForm = this.fb.group({
    id: [],
    unitName: [null, [Validators.required]],
    basePoint: [null, [Validators.required]],
    totalPoint: [],
    m: [null, [Validators.required]],
    cc: [null, [Validators.required]],
    ct: [null, [Validators.required]],
    f: [null, [Validators.required]],
    e: [null, [Validators.required]],
    pv: [null, [Validators.required]],
    a: [null, [Validators.required]],
    cd: [null, [Validators.required]],
    sv: [null, [Validators.required]]
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected unitService: UnitLbrService,
    protected gearService: GearLbrService,
    protected armyListService: ArmyListLbrService
  ) {
    this.newUnit = new UnitLbr();
    this.unitList = new Array<UnitLbr>();
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(res => {
      this.armyList = res.armyList;
      this.getAllUnits();
    });
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

  public saveUnit(): void {
    const unit = this.createFromForm();
    if (this.armyList != null) {
      this.armyListService.addUnit(this.armyList, unit).subscribe(res => (this.armyList = res.body));
    }
  }

  private createFromForm(): IUnitLbr {
    return {
      ...new UnitLbr(),
      id: this.editForm.get(['id'])!.value,
      unitName: this.editForm.get(['unitName'])!.value,
      basePoint: this.editForm.get(['basePoint'])!.value,
      totalPoint: this.editForm.get(['totalPoint'])!.value,
      m: this.editForm.get(['m'])!.value,
      cc: this.editForm.get(['cc'])!.value,
      ct: this.editForm.get(['ct'])!.value,
      f: this.editForm.get(['f'])!.value,
      e: this.editForm.get(['e'])!.value,
      pv: this.editForm.get(['pv'])!.value,
      a: this.editForm.get(['a'])!.value,
      cd: this.editForm.get(['cd'])!.value,
      sv: this.editForm.get(['sv'])!.value
    };
  }
}
