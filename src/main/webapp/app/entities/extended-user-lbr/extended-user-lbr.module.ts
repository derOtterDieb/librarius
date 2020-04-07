import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrariusSharedModule } from 'app/shared/shared.module';
import { ExtendedUserLbrComponent } from './extended-user-lbr.component';
import { ExtendedUserLbrDetailComponent } from './extended-user-lbr-detail.component';
import { ExtendedUserLbrUpdateComponent } from './extended-user-lbr-update.component';
import { ExtendedUserLbrDeleteDialogComponent } from './extended-user-lbr-delete-dialog.component';
import { extendedUserRoute } from './extended-user-lbr.route';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(extendedUserRoute)],
  declarations: [
    ExtendedUserLbrComponent,
    ExtendedUserLbrDetailComponent,
    ExtendedUserLbrUpdateComponent,
    ExtendedUserLbrDeleteDialogComponent
  ],
  entryComponents: [ExtendedUserLbrDeleteDialogComponent]
})
export class LibrariusExtendedUserLbrModule {}
