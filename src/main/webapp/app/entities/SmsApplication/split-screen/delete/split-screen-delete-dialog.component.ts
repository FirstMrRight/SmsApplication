import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISplitScreen } from '../split-screen.model';
import { SplitScreenService } from '../service/split-screen.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './split-screen-delete-dialog.component.html',
})
export class SplitScreenDeleteDialogComponent {
  splitScreen?: ISplitScreen;

  constructor(protected splitScreenService: SplitScreenService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.splitScreenService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
