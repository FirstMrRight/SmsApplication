import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISplitScreen } from '../split-screen.model';

@Component({
  selector: 'jhi-split-screen-detail',
  templateUrl: './split-screen-detail.component.html',
})
export class SplitScreenDetailComponent implements OnInit {
  splitScreen: ISplitScreen | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ splitScreen }) => {
      this.splitScreen = splitScreen;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
