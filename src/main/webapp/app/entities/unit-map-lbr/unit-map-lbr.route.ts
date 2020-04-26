import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { IUnitLbr, UnitLbr } from 'app/shared/model/unit-lbr.model';
import { UnitMapLbrService } from './unit-map-lbr.service';

@Injectable({ providedIn: 'root' })
export class UnitMapLbrResolve implements Resolve<IUnitLbr> {
  constructor(private service: UnitMapLbrService, private router: Router) {}

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

export const unitMapRoute: Routes = [];
