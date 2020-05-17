import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';

type EntityResponseType = HttpResponse<IUnitMapLbr>;
type EntityArrayResponseType = HttpResponse<IUnitMapLbr[]>;

@Injectable({ providedIn: 'root' })
export class UnitMapLbrService {
  public resourceUrl = SERVER_API_URL + 'api/unit-maps';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/unit-maps';

  constructor(protected http: HttpClient) {}

  create(unit: IUnitMapLbr): Observable<EntityResponseType> {
    return this.http.post<IUnitMapLbr>(this.resourceUrl, unit, { observe: 'response' });
  }

  update(unit: IUnitMapLbr): Observable<EntityResponseType> {
    return this.http.put<IUnitMapLbr>(this.resourceUrl, unit, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IUnitMapLbr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnitMapLbr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnitMapLbr[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  findUnitMapWithoutSquadronByListId(listId: string): Observable<EntityArrayResponseType> {
    return this.http.get<IUnitMapLbr[]>(`${this.resourceUrl}/list/${listId}/no-squad`, { observe: 'response' });
  }

  createOrAddToSquadronMap(unitMapId: string, squadId: string, userId: string, listId: string): Observable<HttpResponse<any>> {
    return this.http.put<any>(`${this.resourceUrl}/unitMapId/${unitMapId}/affiliate/${squadId}/userId/${userId}/listId/${listId}`, {
      observe: 'response'
    });
  }
}
