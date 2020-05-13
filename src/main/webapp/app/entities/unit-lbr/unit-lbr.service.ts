import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IUnitLbr } from 'app/shared/model/unit-lbr.model';

type EntityResponseType = HttpResponse<IUnitLbr>;
type EntityArrayResponseType = HttpResponse<IUnitLbr[]>;

@Injectable({ providedIn: 'root' })
export class UnitLbrService {
  public resourceUrl = SERVER_API_URL + 'api/units';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/units';

  constructor(protected http: HttpClient) {}

  create(unit: IUnitLbr): Observable<EntityResponseType> {
    return this.http.post<IUnitLbr>(this.resourceUrl, unit, { observe: 'response' });
  }

  update(unit: IUnitLbr): Observable<EntityResponseType> {
    return this.http.put<IUnitLbr>(this.resourceUrl, unit, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IUnitLbr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnitLbr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnitLbr[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  getAllByName(name: string): Observable<EntityArrayResponseType> {
    return this.http.get<IUnitLbr[]>(`${this.resourceUrl}/search/${name}`, { observe: 'response' });
  }
}
