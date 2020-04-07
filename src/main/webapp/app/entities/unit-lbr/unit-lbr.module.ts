import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrariusSharedModule } from 'app/shared/shared.module';
import { UnitLbrComponent } from './unit-lbr.component';
import { UnitLbrDetailComponent } from './unit-lbr-detail.component';
import { UnitLbrUpdateComponent } from './unit-lbr-update.component';
import { UnitLbrDeleteDialogComponent } from './unit-lbr-delete-dialog.component';
import { unitRoute } from './unit-lbr.route';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(unitRoute)],
  declarations: [UnitLbrComponent, UnitLbrDetailComponent, UnitLbrUpdateComponent, UnitLbrDeleteDialogComponent],
  entryComponents: [UnitLbrDeleteDialogComponent]
})
export class LibrariusUnitLbrModule {}
