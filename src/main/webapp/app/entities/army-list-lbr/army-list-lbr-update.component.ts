import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IArmyListLbr, ArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { ArmyListLbrService } from './army-list-lbr.service';
import { IUnitLbr } from 'app/shared/model/unit-lbr.model';
import { UnitLbrService } from 'app/entities/unit-lbr/unit-lbr.service';
import { IExtendedUserLbr } from 'app/shared/model/extended-user-lbr.model';
import { ExtendedUserLbrService } from 'app/entities/extended-user-lbr/extended-user-lbr.service';

type SelectableEntity = IUnitLbr | IExtendedUserLbr;

@Component({
  selector: 'jhi-army-list-lbr-update',
  templateUrl: './army-list-lbr-update.component.html'
})
export class ArmyListLbrUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    listName: [null, [Validators.required]],
    totalPoint: []
  });

  constructor(
    protected armyListService: ArmyListLbrService,
    protected unitService: UnitLbrService,
    protected extendedUserService: ExtendedUserLbrService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armyList }) => {
      this.updateForm(armyList);
    });
  }

  updateForm(armyList: IArmyListLbr): void {
    this.editForm.patchValue({
      listName: armyList.listName,
      totalPoint: armyList.totalPoint
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const armyList = this.createFromForm();
    if (armyList.id !== undefined) {
      this.subscribeToSaveResponse(this.armyListService.update(armyList));
    } else {
      this.subscribeToSaveResponse(this.armyListService.create(armyList));
    }
  }

  private createFromForm(): IArmyListLbr {
    return {
      ...new ArmyListLbr(),
      id: this.editForm.get(['id'])!.value,
      listName: this.editForm.get(['listName'])!.value,
      totalPoint: this.editForm.get(['totalPoint'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArmyListLbr>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IUnitLbr[], option: IUnitLbr): IUnitLbr {
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
