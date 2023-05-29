import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISplitScreen } from '../split-screen.model';
import { SplitScreenService } from '../service/split-screen.service';

@Injectable({ providedIn: 'root' })
export class SplitScreenRoutingResolveService implements Resolve<ISplitScreen | null> {
  constructor(protected service: SplitScreenService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISplitScreen | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((splitScreen: HttpResponse<ISplitScreen>) => {
          if (splitScreen.body) {
            return of(splitScreen.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
