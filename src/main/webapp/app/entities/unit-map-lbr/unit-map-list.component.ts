import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';
import { Subscription } from 'rxjs';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IGearLbr } from 'app/shared/model/gear-lbr.model';
import { UnitMapLbrService } from 'app/entities/unit-map-lbr/unit-map-lbr.service';

@Component({
  selector: 'jhi-unit-map-list',
  templateUrl: './unit-map-list.component.html'
})
export class UnitMapListComponent implements OnInit, OnDestroy {
  unitMaps?: IUnitMapLbr[];
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
    protected unitMapLbrService: UnitMapLbrService
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

  trackId(index: number, item: IGearLbr): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }
  registerChange(): void {
    this.eventSubscriber = this.eventManager.subscribe('listModification', () => this.loadPage());
  }

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;
    this.unitMapLbrService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IGearLbr[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  delete(unitMap: IUnitMapLbr): void {
    if (unitMap.id) {
      this.unitMapLbrService.delete(unitMap.id).subscribe(() => this.loadPage());
    }
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IGearLbr[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.ngbPaginationPage = this.page;
    this.router.navigate(['/unit-map-lbr'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.unitMaps = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
