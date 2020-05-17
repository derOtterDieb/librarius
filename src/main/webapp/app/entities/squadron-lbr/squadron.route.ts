import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { JhiResolvePagingParams } from 'ng-jhipster';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISquadronLbr, SquadronLbr } from 'app/shared/model/squadron-lbr.model';
import { SquadronLbrService } from 'app/entities/squadron-lbr/squadron-lbr.service';
import { SquadronListComponent } from 'app/entities/squadron-lbr/squadron-list.component';
import { SquadronEditComponent } from 'app/entities/squadron-lbr/squadron-edit.component';

@Injectable({ providedIn: 'root' })
export class SquadronLbrResolve implements Resolve<ISquadronLbr> {
  constructor(private service: SquadronLbrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISquadronLbr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((unit: HttpResponse<ISquadronLbr>) => {
          if (unit.body) {
            return of(unit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SquadronLbr());
  }
}

export const squadronRoute: Routes = [
  {
    path: '',
    component: SquadronListComponent,
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
    component: SquadronEditComponent,
    resolve: {
      unitMap: SquadronLbrResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'new unit map'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SquadronEditComponent,
    resolve: {
      unitMap: SquadronLbrResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'edit unit map'
    },
    canActivate: [UserRouteAccessService]
  }
];
