import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';
import { UnitLbrService } from './unit-lbr.service';
import { IGearLbr } from 'app/shared/model/gear-lbr.model';
import { GearLbrService } from 'app/entities/gear-lbr/gear-lbr.service';

@Component({
  selector: 'jhi-unit-lbr-update',
  templateUrl: './unit-lbr-update.component.html'
})
export class UnitLbrUpdateComponent implements OnInit {
  isSaving = false;
  gears: IGearLbr[] = [];

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
    protected unitService: UnitLbrService,
    protected gearService: GearLbrService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unit }) => {
      this.updateForm(unit);

      this.gearService.query().subscribe((res: HttpResponse<IGearLbr[]>) => (this.gears = res.body || []));
    });
  }

  updateForm(unit: IUnitLbr): void {
    this.editForm.patchValue({
      id: unit.id,
      unitName: unit.unitName,
      basePoint: unit.basePoint,
      totalPoint: unit.totalPoint,
      m: unit.m,
      cc: unit.cc,
      ct: unit.ct,
      f: unit.f,
      e: unit.e,
      pv: unit.pv,
      a: unit.a,
      cd: unit.cd,
      sv: unit.sv
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unit = this.createFromForm();
    if (unit.id !== undefined) {
      this.subscribeToSaveResponse(this.unitService.update(unit));
    } else {
      this.subscribeToSaveResponse(this.unitService.create(unit));
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnitLbr>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IGearLbr): any {
    return item.id;
  }

  getSelected(selectedVals: IGearLbr[], option: IGearLbr): IGearLbr {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
