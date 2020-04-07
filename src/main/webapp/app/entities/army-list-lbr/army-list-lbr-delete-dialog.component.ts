import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { ArmyListLbrService } from './army-list-lbr.service';

@Component({
  templateUrl: './army-list-lbr-delete-dialog.component.html'
})
export class ArmyListLbrDeleteDialogComponent {
  armyList?: IArmyListLbr;

  constructor(protected armyListService: ArmyListLbrService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.armyListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('armyListListModification');
      this.activeModal.close();
    });
  }
}
