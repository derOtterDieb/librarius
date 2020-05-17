import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UnitMapLbrComponent } from './unit-map-lbr.component';
import { LibrariusSharedModule } from 'app/shared/shared.module';
import { unitMapRoute } from './unit-map-lbr.route';
import { UnitMapListComponent } from 'app/entities/unit-map-lbr/unit-map-list.component';
import { UnitMapEditComponent } from 'app/entities/unit-map-lbr/unit-map-edit.component';
import { UnitMapViewComponent } from 'app/entities/unit-map-lbr/unit-map-view.component';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(unitMapRoute)],
  declarations: [UnitMapLbrComponent, UnitMapListComponent, UnitMapEditComponent, UnitMapViewComponent],
  exports: [UnitMapLbrComponent, UnitMapViewComponent],
  entryComponents: []
})
export class LibrariusUnitMapLbrModule {}
