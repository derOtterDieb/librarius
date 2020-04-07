import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IExtendedUserLbr } from 'app/shared/model/extended-user-lbr.model';

type EntityResponseType = HttpResponse<IExtendedUserLbr>;
type EntityArrayResponseType = HttpResponse<IExtendedUserLbr[]>;

@Injectable({ providedIn: 'root' })
export class ExtendedUserLbrService {
  public resourceUrl = SERVER_API_URL + 'api/extended-users';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/extended-users';

  constructor(protected http: HttpClient) {}

  create(extendedUser: IExtendedUserLbr): Observable<EntityResponseType> {
    return this.http.post<IExtendedUserLbr>(this.resourceUrl, extendedUser, { observe: 'response' });
  }

  update(extendedUser: IExtendedUserLbr): Observable<EntityResponseType> {
    return this.http.put<IExtendedUserLbr>(this.resourceUrl, extendedUser, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IExtendedUserLbr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExtendedUserLbr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExtendedUserLbr[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
