import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UnitMapLbrComponent } from './unit-map-lbr.component';
import { LibrariusSharedModule } from 'app/shared/shared.module';
import { unitMapRoute } from './unit-map-lbr.route';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(unitMapRoute)],
  declarations: [UnitMapLbrComponent],
  exports: [UnitMapLbrComponent],
  entryComponents: []
})
export class LibrariusUnitMapLbrModule {}
