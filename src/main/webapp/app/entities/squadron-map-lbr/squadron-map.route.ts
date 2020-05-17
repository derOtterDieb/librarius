import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { JhiResolvePagingParams } from 'ng-jhipster';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISquadronMapLbr, SquadronMapLbr } from 'app/shared/model/squadron-map-lbr.model';
import { SquadronMapLbrService } from 'app/entities/squadron-map-lbr/squadron-map-lbr.service';
import { SquadronMapListComponent } from 'app/entities/squadron-map-lbr/squadron-map-list.component';
import { SquadronMapEditComponent } from 'app/entities/squadron-map-lbr/squadron-map-edit.component';

@Injectable({ providedIn: 'root' })
export class SquadronMapResolve implements Resolve<ISquadronMapLbr> {
  constructor(private service: SquadronMapLbrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISquadronMapLbr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((unit: HttpResponse<ISquadronMapLbr>) => {
          if (unit.body) {
            return of(unit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SquadronMapLbr());
  }
}

export const squadronMapRoute: Routes = [
  {
    path: '',
    component: SquadronMapListComponent,
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
    component: SquadronMapEditComponent,
    resolve: {
      unitMap: SquadronMapResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'new unit map'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SquadronMapEditComponent,
    resolve: {
      unitMap: SquadronMapResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'edit unit map'
    },
    canActivate: [UserRouteAccessService]
  }
];
