import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { SquadronMapLbr } from 'app/shared/model/squadron-map-lbr.model';
import { SquadronLbr } from 'app/shared/model/squadron-lbr.model';
import { SquadronLbrService } from 'app/entities/squadron-lbr/squadron-lbr.service';
import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';
import { SquadronDialogComponent } from 'app/entities/squadron-lbr/squadron-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { computeGearPoint } from 'app/shared/util/compute-points-util';
import { SquadronMapLbrService } from 'app/entities/squadron-map-lbr/squadron-map-lbr.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { UnitMapLbrService } from 'app/entities/unit-map-lbr/unit-map-lbr.service';

@Component({
  selector: 'jhi-squadron-lbr',
  templateUrl: './squadron-lbr.component.html'
})
export class SquadronLbrComponent implements OnInit {
  @Input() userId!: string;
  @Input() armyListId!: string;
  @Output() emit: EventEmitter<any>;
  public squadrons: Observable<any>;
  public isCreatingSquadron = false;
  public newSquadronName: string;
  public unitWithoutSquadron: Observable<any>;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected squadronLbrService: SquadronLbrService,
    protected modalService: NgbModal,
    protected squadronMapLbrService: SquadronMapLbrService,
    protected unitMapLbrService: UnitMapLbrService
  ) {
    this.emit = new EventEmitter<any>();
    this.squadrons = new Observable<SquadronMapLbr[]>();
    this.newSquadronName = '';
    this.unitWithoutSquadron = new Observable<IUnitMapLbr[]>();
  }

  ngOnInit(): void {
    this.getSquadrons();
    this.getUnitWithoutSquadron();
  }

  public getSquadrons(): void {
    this.squadrons = this.squadronMapLbrService.findByUserIdAndListId(this.userId, this.armyListId).pipe(map(res => res.body));
  }

  public getUnitWithoutSquadron(): void {
    this.unitWithoutSquadron = this.unitMapLbrService.findUnitMapWithoutSquadronByListId(this.armyListId).pipe(map(res => res.body));
  }

  public addUnitMapToSquadron(unitMap: IUnitMapLbr): void {
    const modalRef = this.modalService.open(SquadronDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unitMap = unitMap;
    modalRef.componentInstance.userId = this.userId;
    modalRef.componentInstance.listId = this.armyListId;
  }

  public createSquadron(): void {
    const squadron = new SquadronLbr();
    squadron.userId = this.userId;
    squadron.listId = this.armyListId;
    squadron.name = this.newSquadronName;
    this.squadronLbrService.create(squadron).subscribe(
      () => {},
      () => {},
      () => {
        this.newSquadronName = '';
        this.isCreatingSquadron = false;
      }
    );
  }

  public computeGearPoint(unitMap: IUnitMapLbr): number {
    return computeGearPoint(unitMap);
  }
}
