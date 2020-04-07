import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGearLbr } from 'app/shared/model/gear-lbr.model';
import { GearLbrService } from './gear-lbr.service';

@Component({
  templateUrl: './gear-lbr-delete-dialog.component.html'
})
export class GearLbrDeleteDialogComponent {
  gear?: IGearLbr;

  constructor(protected gearService: GearLbrService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.gearService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gearListModification');
      this.activeModal.close();
    });
  }
}
