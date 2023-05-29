import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SplitScreenComponent } from './list/split-screen.component';
import { SplitScreenDetailComponent } from './detail/split-screen-detail.component';
import { SplitScreenUpdateComponent } from './update/split-screen-update.component';
import { SplitScreenDeleteDialogComponent } from './delete/split-screen-delete-dialog.component';
import { SplitScreenRoutingModule } from './route/split-screen-routing.module';

@NgModule({
  imports: [SharedModule, SplitScreenRoutingModule],
  declarations: [SplitScreenComponent, SplitScreenDetailComponent, SplitScreenUpdateComponent, SplitScreenDeleteDialogComponent],
})
export class SmsApplicationSplitScreenModule {}
