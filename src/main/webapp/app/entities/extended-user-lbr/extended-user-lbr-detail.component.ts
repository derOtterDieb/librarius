import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtendedUserLbr } from 'app/shared/model/extended-user-lbr.model';

@Component({
  selector: 'jhi-extended-user-lbr-detail',
  templateUrl: './extended-user-lbr-detail.component.html'
})
export class ExtendedUserLbrDetailComponent implements OnInit {
  extendedUser: IExtendedUserLbr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extendedUser }) => (this.extendedUser = extendedUser));
  }

  previousState(): void {
    window.history.back();
  }
}
