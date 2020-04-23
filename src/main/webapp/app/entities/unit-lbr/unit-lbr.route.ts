import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';
import { UnitLbrService } from './unit-lbr.service';
import { UnitLbrComponent } from './unit-lbr.component';
import { UnitLbrDetailComponent } from './unit-lbr-detail.component';
import { UnitLbrUpdateComponent } from './unit-lbr-update.component';

@Injectable({ providedIn: 'root' })
export class UnitLbrResolve implements Resolve<IUnitLbr> {
  constructor(private service: UnitLbrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnitLbr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((unit: HttpResponse<UnitLbr>) => {
          if (unit.body) {
            return of(unit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UnitLbr());
  }
}

export const unitRoute: Routes = [
  {
    path: '',
    component: UnitLbrComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'librariusApp.unit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UnitLbrDetailComponent,
    resolve: {
      unit: UnitLbrResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'librariusApp.unit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnitLbrUpdateComponent,
    resolve: {
      unit: UnitLbrResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'librariusApp.unit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnitLbrUpdateComponent,
    resolve: {
      unit: UnitLbrResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'librariusApp.unit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
