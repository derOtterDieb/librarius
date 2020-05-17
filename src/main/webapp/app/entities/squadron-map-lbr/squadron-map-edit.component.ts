import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { SquadronMapLbrService } from 'app/entities/squadron-map-lbr/squadron-map-lbr.service';
import { ISquadronLbr } from 'app/shared/model/squadron-lbr.model';
import { ISquadronMapLbr, SquadronMapLbr } from 'app/shared/model/squadron-map-lbr.model';

@Component({
  selector: 'jhi-squadron-map-edit',
  templateUrl: './squadron-map-edit.component.html'
})
export class SquadronMapEditComponent implements OnInit {
  isSaving = false;
  editForm = this.fb.group({
    id: [],
    squadron: [null, [Validators.required]],
    unitMaps: [null, [Validators.required]]
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder,
    private squadronMapLbrService: SquadronMapLbrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitMap }) => {
      this.updateForm(unitMap);
    });
  }

  updateForm(squadronMapLbr: ISquadronMapLbr): void {
    this.editForm.patchValue({
      id: squadronMapLbr.id,
      squadron: squadronMapLbr.squadron,
      unitMaps: squadronMapLbr.unitMaps
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unitMap = this.createFromForm();
    if (unitMap.id !== undefined) {
      this.subscribeToSaveResponse(this.squadronMapLbrService.update(unitMap));
    } else {
      this.subscribeToSaveResponse(this.squadronMapLbrService.create(unitMap));
    }
  }

  private createFromForm(): ISquadronMapLbr {
    return {
      ...new SquadronMapLbr(),
      id: this.editForm.get(['id'])!.value,
      squadron: this.editForm.get(['squadron'])!.value,
      unitMaps: this.editForm.get(['unitMaps'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISquadronLbr>>): void {
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
