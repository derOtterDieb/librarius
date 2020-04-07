import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGearLbr, GearLbr } from 'app/shared/model/gear-lbr.model';
import { GearLbrService } from './gear-lbr.service';
import { GearLbrComponent } from './gear-lbr.component';
import { GearLbrDetailComponent } from './gear-lbr-detail.component';
import { GearLbrUpdateComponent } from './gear-lbr-update.component';

@Injectable({ providedIn: 'root' })
export class GearLbrResolve implements Resolve<IGearLbr> {
  constructor(private service: GearLbrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGearLbr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gear: HttpResponse<GearLbr>) => {
          if (gear.body) {
            return of(gear.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GearLbr());
  }
}

export const gearRoute: Routes = [
  {
    path: '',
    component: GearLbrComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'librariusApp.gear.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GearLbrDetailComponent,
    resolve: {
      gear: GearLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.gear.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GearLbrUpdateComponent,
    resolve: {
      gear: GearLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.gear.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GearLbrUpdateComponent,
    resolve: {
      gear: GearLbrResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'librariusApp.gear.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
