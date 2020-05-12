import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IUnitLbr } from 'app/shared/model/unit-lbr.model';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';

type EntityResponseType = HttpResponse<IArmyListLbr>;
type EntityArrayResponseType = HttpResponse<IArmyListLbr[]>;

@Injectable({ providedIn: 'root' })
export class ArmyListLbrService {
  public resourceUrl = SERVER_API_URL + 'api/army-lists';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/army-lists';

  constructor(protected http: HttpClient) {}

  create(armyList: IArmyListLbr): Observable<EntityResponseType> {
    return this.http.post<IArmyListLbr>(this.resourceUrl, armyList, { observe: 'response' });
  }

  update(armyList: IArmyListLbr): Observable<EntityResponseType> {
    return this.http.put<IArmyListLbr>(this.resourceUrl, armyList, { observe: 'response' });
  }

  find(id: string | null): Observable<EntityResponseType> {
    return this.http.get<IArmyListLbr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmyListLbr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmyListLbr[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  getFromUserId(id: string): Observable<EntityArrayResponseType> {
    return this.http.get<IArmyListLbr[]>(`${this.resourceUrl}/user/${id}`, { observe: 'response' });
  }

  addUnit(armyList: IArmyListLbr, numberOfUnit: number, unit: IUnitLbr): Observable<EntityResponseType> {
    return this.http.put<IArmyListLbr>(`${this.resourceUrl}/add-unit/${armyList.id}/${numberOfUnit}`, unit, { observe: 'response' });
  }

  removeUnit(armyList: IArmyListLbr, unit: IUnitLbr): Observable<EntityResponseType> {
    return this.http.put<IArmyListLbr>(`${this.resourceUrl}/remove-unit/${armyList.id}`, unit, { observe: 'response' });
  }
}
