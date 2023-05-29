import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SplitScreenFormService, SplitScreenFormGroup } from './split-screen-form.service';
import { ISplitScreen } from '../split-screen.model';
import { SplitScreenService } from '../service/split-screen.service';

@Component({
  selector: 'jhi-split-screen-update',
  templateUrl: './split-screen-update.component.html',
})
export class SplitScreenUpdateComponent implements OnInit {
  isSaving = false;
  splitScreen: ISplitScreen | null = null;

  editForm: SplitScreenFormGroup = this.splitScreenFormService.createSplitScreenFormGroup();

  constructor(
    protected splitScreenService: SplitScreenService,
    protected splitScreenFormService: SplitScreenFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ splitScreen }) => {
      this.splitScreen = splitScreen;
      if (splitScreen) {
        this.updateForm(splitScreen);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const splitScreen = this.splitScreenFormService.getSplitScreen(this.editForm);
    if (splitScreen.id !== null) {
      this.subscribeToSaveResponse(this.splitScreenService.update(splitScreen));
    } else {
      this.subscribeToSaveResponse(this.splitScreenService.create(splitScreen));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISplitScreen>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(splitScreen: ISplitScreen): void {
    this.splitScreen = splitScreen;
    this.splitScreenFormService.resetForm(this.editForm, splitScreen);
  }
}
