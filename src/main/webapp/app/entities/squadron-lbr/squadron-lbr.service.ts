import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISquadronLbr } from 'app/shared/model/squadron-lbr.model';

type EntityResponseType = HttpResponse<ISquadronLbr>;
type EntityArrayResponseType = HttpResponse<ISquadronLbr[]>;

@Injectable({ providedIn: 'root' })
export class SquadronLbrService {
  public resourceUrl = SERVER_API_URL + 'api/squadrons';

  constructor(protected http: HttpClient) {}

  create(squadron: ISquadronLbr): Observable<EntityResponseType> {
    return this.http.post<ISquadronLbr>(this.resourceUrl, squadron, { observe: 'response' });
  }

  update(squadron: ISquadronLbr): Observable<EntityResponseType> {
    return this.http.put<ISquadronLbr>(this.resourceUrl, squadron, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISquadronLbr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISquadronLbr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findByUserIdAndListId(userId: string, listId: string): Observable<EntityArrayResponseType> {
    return this.http.get<ISquadronLbr[]>(`${this.resourceUrl}/userId/${userId}/listId/${listId}`, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
