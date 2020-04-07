import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrariusSharedModule } from 'app/shared/shared.module';
import { GearLbrComponent } from './gear-lbr.component';
import { GearLbrDetailComponent } from './gear-lbr-detail.component';
import { GearLbrUpdateComponent } from './gear-lbr-update.component';
import { GearLbrDeleteDialogComponent } from './gear-lbr-delete-dialog.component';
import { gearRoute } from './gear-lbr.route';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(gearRoute)],
  declarations: [GearLbrComponent, GearLbrDetailComponent, GearLbrUpdateComponent, GearLbrDeleteDialogComponent],
  entryComponents: [GearLbrDeleteDialogComponent]
})
export class LibrariusGearLbrModule {}
