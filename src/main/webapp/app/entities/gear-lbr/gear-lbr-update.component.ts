import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGearLbr, GearLbr } from 'app/shared/model/gear-lbr.model';
import { GearLbrService } from './gear-lbr.service';

@Component({
  selector: 'jhi-gear-lbr-update',
  templateUrl: './gear-lbr-update.component.html'
})
export class GearLbrUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gearName: [null, [Validators.required]],
    pointValue: [null, [Validators.required]],
    type: [null, [Validators.required]],
    f: [null, [Validators.required]],
    range: [null, [Validators.required]],
    pa: [null, [Validators.required]],
    d: [null, [Validators.required]]
  });

  constructor(protected gearService: GearLbrService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gear }) => {
      this.updateForm(gear);
    });
  }

  updateForm(gear: IGearLbr): void {
    this.editForm.patchValue({
      id: gear.id,
      gearName: gear.gearName,
      pointValue: gear.pointValue,
      type: gear.type,
      f: gear.f,
      range: gear.range,
      pa: gear.pa,
      d: gear.d
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gear = this.createFromForm();
    if (gear.id !== undefined) {
      this.subscribeToSaveResponse(this.gearService.update(gear));
    } else {
      this.subscribeToSaveResponse(this.gearService.create(gear));
    }
  }

  private createFromForm(): IGearLbr {
    return {
      ...new GearLbr(),
      id: this.editForm.get(['id'])!.value,
      gearName: this.editForm.get(['gearName'])!.value,
      pointValue: this.editForm.get(['pointValue'])!.value,
      type: this.editForm.get(['type'])!.value,
      f: this.editForm.get(['f'])!.value,
      range: this.editForm.get(['range'])!.value,
      pa: this.editForm.get(['pa'])!.value,
      d: this.editForm.get(['d'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGearLbr>>): void {
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
