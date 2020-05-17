import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ISquadronLbr } from 'app/shared/model/squadron-lbr.model';
import { SquadronLbrService } from 'app/entities/squadron-lbr/squadron-lbr.service';

@Component({
  selector: 'jhi-squadron-list',
  templateUrl: './squadron-list.component.html'
})
export class SquadronListComponent implements OnInit, OnDestroy {
  squadrons?: ISquadronLbr[];
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
    protected squadronLbrService: SquadronLbrService
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

  trackId(index: number, item: ISquadronLbr): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }
  registerChange(): void {
    this.eventSubscriber = this.eventManager.subscribe('listModification', () => this.loadPage());
  }

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;
    this.squadronLbrService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISquadronLbr[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  delete(squadronLbr: ISquadronLbr): void {
    if (squadronLbr.id) {
      this.squadronLbrService.delete(squadronLbr.id).subscribe(() => this.loadPage());
    }
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISquadronLbr[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.ngbPaginationPage = this.page;
    this.router.navigate(['/squadron-lbr'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.squadrons = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
