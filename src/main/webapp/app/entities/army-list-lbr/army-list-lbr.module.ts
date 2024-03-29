import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrariusSharedModule } from 'app/shared/shared.module';
import { ArmyListLbrComponent } from './army-list-lbr.component';
import { ArmyListLbrDetailComponent } from './army-list-lbr-detail.component';
import { ArmyListLbrUpdateComponent } from './army-list-lbr-update.component';
import { ArmyListLbrDeleteDialogComponent } from './army-list-lbr-delete-dialog.component';
import { ArmyListLbrAssociateUnitDialogComponent } from './army-list-lbr-associate-unit-dialog.component';
import { armyListRoute } from './army-list-lbr.route';
import { LibrariusUnitMapLbrModule } from 'app/entities/unit-map-lbr/unit-map-lbr.module';
import { LibrariusSquadronLbrModule } from 'app/entities/squadron-lbr/squadron-lbr.module';
import { LibrariusSquadronMapModule } from 'app/entities/squadron-map-lbr/squadron-map.module';

@NgModule({
  imports: [
    LibrariusSharedModule,
    RouterModule.forChild(armyListRoute),
    LibrariusUnitMapLbrModule,
    LibrariusSquadronLbrModule,
    LibrariusSquadronMapModule
  ],
  declarations: [
    ArmyListLbrComponent,
    ArmyListLbrDetailComponent,
    ArmyListLbrUpdateComponent,
    ArmyListLbrDeleteDialogComponent,
    ArmyListLbrAssociateUnitDialogComponent
  ],
  entryComponents: [ArmyListLbrDeleteDialogComponent, ArmyListLbrAssociateUnitDialogComponent]
})
export class LibrariusArmyListLbrModule {}
