import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExtendedUserLbr } from 'app/shared/model/extended-user-lbr.model';
import { ExtendedUserLbrService } from './extended-user-lbr.service';

@Component({
  templateUrl: './extended-user-lbr-delete-dialog.component.html'
})
export class ExtendedUserLbrDeleteDialogComponent {
  extendedUser?: IExtendedUserLbr;

  constructor(
    protected extendedUserService: ExtendedUserLbrService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.extendedUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('extendedUserListModification');
      this.activeModal.close();
    });
  }
}
