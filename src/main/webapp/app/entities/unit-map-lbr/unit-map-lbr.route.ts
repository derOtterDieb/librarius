import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UnitMapLbrService } from './unit-map-lbr.service';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { UnitMapListComponent } from 'app/entities/unit-map-lbr/unit-map-list.component';
import { UnitMapEditComponent } from 'app/entities/unit-map-lbr/unit-map-edit.component';
import { IUnitMapLbr, UnitMapLBr } from 'app/shared/model/unit-map-lbr.model';
import { UnitMapViewComponent } from 'app/entities/unit-map-lbr/unit-map-view.component';

@Injectable({ providedIn: 'root' })
export class UnitMapLbrResolve implements Resolve<IUnitMapLbr> {
  constructor(private service: UnitMapLbrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnitMapLbr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((unit: HttpResponse<IUnitMapLbr>) => {
          if (unit.body) {
            return of(unit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UnitMapLBr());
  }
}

export const unitMapRoute: Routes = [
  {
    path: '',
    component: UnitMapListComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'unit map list'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnitMapEditComponent,
    resolve: {
      unitMap: UnitMapLbrResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'new unit map'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'view/:id',
    component: UnitMapViewComponent,
    resolve: {
      unitMap: UnitMapLbrResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'Librarius'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnitMapEditComponent,
    resolve: {
      unitMap: UnitMapLbrResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'edit unit map'
    },
    canActivate: [UserRouteAccessService]
  }
];
