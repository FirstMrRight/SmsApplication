import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SplitScreenComponent } from '../list/split-screen.component';
import { SplitScreenDetailComponent } from '../detail/split-screen-detail.component';
import { SplitScreenUpdateComponent } from '../update/split-screen-update.component';
import { SplitScreenRoutingResolveService } from './split-screen-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const splitScreenRoute: Routes = [
  {
    path: '',
    component: SplitScreenComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SplitScreenDetailComponent,
    resolve: {
      splitScreen: SplitScreenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SplitScreenUpdateComponent,
    resolve: {
      splitScreen: SplitScreenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SplitScreenUpdateComponent,
    resolve: {
      splitScreen: SplitScreenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(splitScreenRoute)],
  exports: [RouterModule],
})
export class SplitScreenRoutingModule {}
