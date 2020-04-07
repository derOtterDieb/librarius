import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArmyListLbr, ArmyListLbr } from 'app/shared/model/army-list-lbr.model';
import { ArmyListLbrService } from './army-list-lbr.service';
import { ArmyListLbrComponent } from './army-list-lbr.component';
import { ArmyListLbrDetailComponent } from './army-list-lbr-detail.component';
import { ArmyListLbrUpdateComponent } from './army-list-lbr-update.component';

@Injectable({ providedIn: 'root' })
export class ArmyListLbrResolve implements Resolve<IArmyListLbr> {
  constructor(private service: ArmyListLbrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArmyListLbr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((armyList: HttpResponse<ArmyListLbr>) => {
          if (armyList.body) {
            return of(armyList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ArmyListLbr());
  }
}

export const armyListRoute: Routes = [
  {
    path: '',
    component: ArmyListLbrComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'librariusApp.armyList.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArmyListLbrDetailComponent,
    resolve: {
      armyList: ArmyListLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.armyList.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArmyListLbrUpdateComponent,
    resolve: {
      armyList: ArmyListLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.armyList.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArmyListLbrUpdateComponent,
    resolve: {
      armyList: ArmyListLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.armyList.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
