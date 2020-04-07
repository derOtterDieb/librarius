import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrariusSharedModule } from 'app/shared/shared.module';
import { ArmyListLbrComponent } from './army-list-lbr.component';
import { ArmyListLbrDetailComponent } from './army-list-lbr-detail.component';
import { ArmyListLbrUpdateComponent } from './army-list-lbr-update.component';
import { ArmyListLbrDeleteDialogComponent } from './army-list-lbr-delete-dialog.component';
import { armyListRoute } from './army-list-lbr.route';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(armyListRoute)],
  declarations: [ArmyListLbrComponent, ArmyListLbrDetailComponent, ArmyListLbrUpdateComponent, ArmyListLbrDeleteDialogComponent],
  entryComponents: [ArmyListLbrDeleteDialogComponent]
})
export class LibrariusArmyListLbrModule {}
