import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISquadronMapLbr } from 'app/shared/model/squadron-map-lbr.model';

type EntityResponseType = HttpResponse<ISquadronMapLbr>;
type EntityArrayResponseType = HttpResponse<ISquadronMapLbr[]>;

@Injectable({ providedIn: 'root' })
export class SquadronMapLbrService {
  public resourceUrl = SERVER_API_URL + 'api/squadron-maps';

  constructor(protected http: HttpClient) {}

  create(squadron: ISquadronMapLbr): Observable<EntityResponseType> {
    return this.http.post<ISquadronMapLbr>(this.resourceUrl, squadron, { observe: 'response' });
  }

  update(squadron: ISquadronMapLbr): Observable<EntityResponseType> {
    return this.http.put<ISquadronMapLbr>(this.resourceUrl, squadron, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISquadronMapLbr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISquadronMapLbr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findByUserIdAndListId(userId: string, listId: string): Observable<EntityArrayResponseType> {
    return this.http.get<ISquadronMapLbr[]>(`${this.resourceUrl}/userId/${userId}/listId/${listId}`, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  deleteSquad(squadId: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/cascade/${squadId}`, { observe: 'response' });
  }

  deleteUnitFromSquad(unitId: string, squadId: string): Observable<ISquadronMapLbr> {
    return this.http.put<ISquadronMapLbr>(`${this.resourceUrl}/remove/${unitId}/from/${squadId}`, { observe: 'response' });
  }
}
