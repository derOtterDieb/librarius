import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { IUnitLbr } from 'app/shared/model/unit-lbr.model';
import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { ArmyListLbrService } from './army-list-lbr.service';

@Component({
  templateUrl: './army-list-lbr-associate-unit-dialog.component.html'
})
export class ArmyListLbrAssociateUnitDialogComponent {
  armyList?: IArmyListLbr;
  unit?: IUnitLbr;

  constructor(protected armyListService: ArmyListLbrService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmAssociate(): void {
    if (this.armyList != null) {
      this.armyListService.update(this.armyList).subscribe(() => {
        this.eventManager.broadcast('armyListListModification');
        this.activeModal.close();
      });
    }
  }
}
