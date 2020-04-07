import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExtendedUserLbr, ExtendedUserLbr } from 'app/shared/model/extended-user-lbr.model';
import { ExtendedUserLbrService } from './extended-user-lbr.service';
import { ExtendedUserLbrComponent } from './extended-user-lbr.component';
import { ExtendedUserLbrDetailComponent } from './extended-user-lbr-detail.component';
import { ExtendedUserLbrUpdateComponent } from './extended-user-lbr-update.component';

@Injectable({ providedIn: 'root' })
export class ExtendedUserLbrResolve implements Resolve<IExtendedUserLbr> {
  constructor(private service: ExtendedUserLbrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExtendedUserLbr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((extendedUser: HttpResponse<ExtendedUserLbr>) => {
          if (extendedUser.body) {
            return of(extendedUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExtendedUserLbr());
  }
}

export const extendedUserRoute: Routes = [
  {
    path: '',
    component: ExtendedUserLbrComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'librariusApp.extendedUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExtendedUserLbrDetailComponent,
    resolve: {
      extendedUser: ExtendedUserLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.extendedUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExtendedUserLbrUpdateComponent,
    resolve: {
      extendedUser: ExtendedUserLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.extendedUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExtendedUserLbrUpdateComponent,
    resolve: {
      extendedUser: ExtendedUserLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.extendedUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
