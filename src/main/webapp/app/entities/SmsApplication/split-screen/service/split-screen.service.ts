import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISplitScreen, NewSplitScreen } from '../split-screen.model';

export type PartialUpdateSplitScreen = Partial<ISplitScreen> & Pick<ISplitScreen, 'id'>;

type RestOf<T extends ISplitScreen | NewSplitScreen> = Omit<T, 'updateDate' | 'createDate'> & {
  updateDate?: string | null;
  createDate?: string | null;
};

export type RestSplitScreen = RestOf<ISplitScreen>;

export type NewRestSplitScreen = RestOf<NewSplitScreen>;

export type PartialUpdateRestSplitScreen = RestOf<PartialUpdateSplitScreen>;

export type EntityResponseType = HttpResponse<ISplitScreen>;
export type EntityArrayResponseType = HttpResponse<ISplitScreen[]>;

@Injectable({ providedIn: 'root' })
export class SplitScreenService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/split-screens', 'smsapplication');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(splitScreen: NewSplitScreen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(splitScreen);
    return this.http
      .post<RestSplitScreen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(splitScreen: ISplitScreen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(splitScreen);
    return this.http
      .put<RestSplitScreen>(`${this.resourceUrl}/${this.getSplitScreenIdentifier(splitScreen)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(splitScreen: PartialUpdateSplitScreen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(splitScreen);
    return this.http
      .patch<RestSplitScreen>(`${this.resourceUrl}/${this.getSplitScreenIdentifier(splitScreen)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSplitScreen>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSplitScreen[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSplitScreenIdentifier(splitScreen: Pick<ISplitScreen, 'id'>): number {
    return splitScreen.id;
  }

  compareSplitScreen(o1: Pick<ISplitScreen, 'id'> | null, o2: Pick<ISplitScreen, 'id'> | null): boolean {
    return o1 && o2 ? this.getSplitScreenIdentifier(o1) === this.getSplitScreenIdentifier(o2) : o1 === o2;
  }

  addSplitScreenToCollectionIfMissing<Type extends Pick<ISplitScreen, 'id'>>(
    splitScreenCollection: Type[],
    ...splitScreensToCheck: (Type | null | undefined)[]
  ): Type[] {
    const splitScreens: Type[] = splitScreensToCheck.filter(isPresent);
    if (splitScreens.length > 0) {
      const splitScreenCollectionIdentifiers = splitScreenCollection.map(
        splitScreenItem => this.getSplitScreenIdentifier(splitScreenItem)!
      );
      const splitScreensToAdd = splitScreens.filter(splitScreenItem => {
        const splitScreenIdentifier = this.getSplitScreenIdentifier(splitScreenItem);
        if (splitScreenCollectionIdentifiers.includes(splitScreenIdentifier)) {
          return false;
        }
        splitScreenCollectionIdentifiers.push(splitScreenIdentifier);
        return true;
      });
      return [...splitScreensToAdd, ...splitScreenCollection];
    }
    return splitScreenCollection;
  }

  protected convertDateFromClient<T extends ISplitScreen | NewSplitScreen | PartialUpdateSplitScreen>(splitScreen: T): RestOf<T> {
    return {
      ...splitScreen,
      updateDate: splitScreen.updateDate?.toJSON() ?? null,
      createDate: splitScreen.createDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSplitScreen: RestSplitScreen): ISplitScreen {
    return {
      ...restSplitScreen,
      updateDate: restSplitScreen.updateDate ? dayjs(restSplitScreen.updateDate) : undefined,
      createDate: restSplitScreen.createDate ? dayjs(restSplitScreen.createDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSplitScreen>): HttpResponse<ISplitScreen> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSplitScreen[]>): HttpResponse<ISplitScreen[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
