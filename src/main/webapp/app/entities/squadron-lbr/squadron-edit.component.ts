import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { SquadronLbrService } from 'app/entities/squadron-lbr/squadron-lbr.service';
import { ISquadronLbr, SquadronLbr } from 'app/shared/model/squadron-lbr.model';

@Component({
  selector: 'jhi-squadron-edit',
  templateUrl: './squadron-edit.component.html'
})
export class SquadronEditComponent implements OnInit {
  isSaving = false;
  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    userId: [null, [Validators.required]],
    listId: [null, [Validators.required]]
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder,
    private squadronLbrService: SquadronLbrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitMap }) => {
      this.updateForm(unitMap);
    });
  }

  updateForm(squadronLbr: ISquadronLbr): void {
    this.editForm.patchValue({
      id: squadronLbr.id,
      name: squadronLbr.name,
      userId: squadronLbr.userId,
      listId: squadronLbr.listId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unitMap = this.createFromForm();
    if (unitMap.id !== undefined) {
      this.subscribeToSaveResponse(this.squadronLbrService.update(unitMap));
    } else {
      this.subscribeToSaveResponse(this.squadronLbrService.create(unitMap));
    }
  }

  private createFromForm(): ISquadronLbr {
    return {
      ...new SquadronLbr(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      listId: this.editForm.get(['listId'])!.value
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
