import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { FormBuilder, Validators } from '@angular/forms';
import { UnitMapLbrService } from 'app/entities/unit-map-lbr/unit-map-lbr.service';
import { IUnitMapLbr, UnitMapLBr } from 'app/shared/model/unit-map-lbr.model';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-unit-map-edit',
  templateUrl: './unit-map-edit.component.html'
})
export class UnitMapEditComponent implements OnInit {
  isSaving = false;
  editForm = this.fb.group({
    id: [],
    unit: [null, [Validators.required]],
    numberOfUnit: [null, [Validators.required]],
    gears: [null],
    squadronId: [null]
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder,
    private unitMapLbrService: UnitMapLbrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitMap }) => {
      this.updateForm(unitMap);
    });
  }

  updateForm(unitMapLbr: IUnitMapLbr): void {
    this.editForm.patchValue({
      id: unitMapLbr.id,
      unit: unitMapLbr.unit,
      numberOfUnit: unitMapLbr.numberOfUnit,
      gears: unitMapLbr.gears,
      squadronId: unitMapLbr.squadronId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unitMap = this.createFromForm();
    if (unitMap.id !== undefined) {
      this.subscribeToSaveResponse(this.unitMapLbrService.update(unitMap));
    } else {
      this.subscribeToSaveResponse(this.unitMapLbrService.create(unitMap));
    }
  }

  private createFromForm(): IUnitMapLbr {
    return {
      ...new UnitMapLBr(),
      id: this.editForm.get(['id'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      numberOfUnit: this.editForm.get(['numberOfUnit'])!.value,
      gears: this.editForm.get(['gears'])!.value,
      squadronId: this.editForm.get(['squadronId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnitMapLbr>>): void {
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
}
