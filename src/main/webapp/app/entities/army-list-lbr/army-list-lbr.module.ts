import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrariusSharedModule } from 'app/shared/shared.module';
import { ArmyListLbrComponent } from './army-list-lbr.component';
import { ArmyListLbrDetailComponent } from './army-list-lbr-detail.component';
import { ArmyListLbrUpdateComponent } from './army-list-lbr-update.component';
import { ArmyListLbrDeleteDialogComponent } from './army-list-lbr-delete-dialog.component';
import { ArmyListLbrAssociateUnitDialogComponent } from './army-list-lbr-associate-unit-dialog.component';
import { SquadronDialogComponent } from './squadron-dialog.component';
import { armyListRoute } from './army-list-lbr.route';
import { LibrariusUnitMapLbrModule } from 'app/entities/unit-map-lbr/unit-map-lbr.module';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(armyListRoute), LibrariusUnitMapLbrModule],
  declarations: [
    ArmyListLbrComponent,
    ArmyListLbrDetailComponent,
    ArmyListLbrUpdateComponent,
    ArmyListLbrDeleteDialogComponent,
    ArmyListLbrAssociateUnitDialogComponent,
    SquadronDialogComponent
  ],
  entryComponents: [ArmyListLbrDeleteDialogComponent, ArmyListLbrAssociateUnitDialogComponent, SquadronDialogComponent]
})
export class LibrariusArmyListLbrModule {}
