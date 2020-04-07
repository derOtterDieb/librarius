import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExtendedUserLbr, ExtendedUserLbr } from 'app/shared/model/extended-user-lbr.model';
import { ExtendedUserLbrService } from './extended-user-lbr.service';

@Component({
  selector: 'jhi-extended-user-lbr-update',
  templateUrl: './extended-user-lbr-update.component.html'
})
export class ExtendedUserLbrUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    pseudo: [null, [Validators.required]]
  });

  constructor(protected extendedUserService: ExtendedUserLbrService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extendedUser }) => {
      this.updateForm(extendedUser);
    });
  }

  updateForm(extendedUser: IExtendedUserLbr): void {
    this.editForm.patchValue({
      id: extendedUser.id,
      pseudo: extendedUser.pseudo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const extendedUser = this.createFromForm();
    if (extendedUser.id !== undefined) {
      this.subscribeToSaveResponse(this.extendedUserService.update(extendedUser));
    } else {
      this.subscribeToSaveResponse(this.extendedUserService.create(extendedUser));
    }
  }

  private createFromForm(): IExtendedUserLbr {
    return {
      ...new ExtendedUserLbr(),
      id: this.editForm.get(['id'])!.value,
      pseudo: this.editForm.get(['pseudo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtendedUserLbr>>): void {
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
