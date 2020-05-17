import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ISquadronMapLbr, SquadronMapLbr } from 'app/shared/model/squadron-map-lbr.model';
import { SquadronMapLbrService } from 'app/entities/squadron-map-lbr/squadron-map-lbr.service';

@Component({
  selector: 'jhi-squadron-map-list',
  templateUrl: './squadron-map-list.component.html'
})
export class SquadronMapListComponent implements OnInit, OnDestroy {
  squadronMapLbrs?: ISquadronMapLbr[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected squadronMapLbrService: SquadronMapLbrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChange();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISquadronMapLbr): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }
  registerChange(): void {
    this.eventSubscriber = this.eventManager.subscribe('listModification', () => this.loadPage());
  }

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;
    this.squadronMapLbrService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<SquadronMapLbr[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  delete(squadronMapLbr: ISquadronMapLbr): void {
    if (squadronMapLbr.id) {
      this.squadronMapLbrService.delete(squadronMapLbr.id).subscribe(() => this.loadPage());
    }
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISquadronMapLbr[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.ngbPaginationPage = this.page;
    this.router.navigate(['/squadron-map-lbr'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.squadronMapLbrs = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
