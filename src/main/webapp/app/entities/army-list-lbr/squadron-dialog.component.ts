import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';
import { SquadronLbrService } from 'app/entities/squadron-lbr/squadron-lbr.service';
import { Observable } from 'rxjs';
import { ISquadronLbr, SquadronLbr } from 'app/shared/model/squadron-lbr.model';
import { UnitMapLbrService } from 'app/entities/unit-map-lbr/unit-map-lbr.service';

@Component({
  templateUrl: './squadron-dialog.component.html'
})
export class SquadronDialogComponent implements OnInit {
  unitMap?: IUnitMapLbr;
  userId?: string;
  listId?: string;
  squadrons: Observable<any>;
  selectedSquad: ISquadronLbr;

  constructor(
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    protected squadronLbrService: SquadronLbrService,
    protected unitMapLbrService: UnitMapLbrService
  ) {
    this.squadrons = new Observable<any>();
    this.selectedSquad = new SquadronLbr();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  ngOnInit(): void {
    if (this.userId && this.listId) {
      this.squadrons = this.squadronLbrService.findByUserIdAndListId(this.userId, this.listId);
    }
  }

  save(): void {
    if (this.unitMap) {
      this.unitMap.squadronId = this.selectedSquad.id;
      this.unitMapLbrService.update(this.unitMap).subscribe(
        () => {},
        () => {},
        () => this.cancel()
      );
    }
  }
}
