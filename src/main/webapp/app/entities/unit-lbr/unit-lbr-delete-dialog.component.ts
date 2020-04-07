import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnitLbr } from 'app/shared/model/unit-lbr.model';
import { UnitLbrService } from './unit-lbr.service';

@Component({
  templateUrl: './unit-lbr-delete-dialog.component.html'
})
export class UnitLbrDeleteDialogComponent {
  unit?: IUnitLbr;

  constructor(protected unitService: UnitLbrService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.unitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('unitListModification');
      this.activeModal.close();
    });
  }
}
