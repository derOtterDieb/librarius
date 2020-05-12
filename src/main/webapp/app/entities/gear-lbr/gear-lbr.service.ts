import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IGearLbr } from 'app/shared/model/gear-lbr.model';

type EntityResponseType = HttpResponse<IGearLbr>;
type EntityArrayResponseType = HttpResponse<IGearLbr[]>;

@Injectable({ providedIn: 'root' })
export class GearLbrService {
  public resourceUrl = SERVER_API_URL + 'api/gears';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/gears';

  constructor(protected http: HttpClient) {}

  create(gear: IGearLbr): Observable<EntityResponseType> {
    return this.http.post<IGearLbr>(this.resourceUrl, gear, { observe: 'response' });
  }

  update(gear: IGearLbr): Observable<EntityResponseType> {
    return this.http.put<IGearLbr>(this.resourceUrl, gear, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGearLbr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGearLbr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAllByName(name: string): Observable<EntityArrayResponseType> {
    return this.http.get<IGearLbr[]>(`${this.resourceUrl}/search/${name}`, { observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGearLbr[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
