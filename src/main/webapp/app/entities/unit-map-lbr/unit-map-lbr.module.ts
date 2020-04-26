import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrariusSharedModule } from 'app/shared/shared.module';
import { unitMapRoute } from './unit-map-lbr.route';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(unitMapRoute)],
  declarations: [],
  entryComponents: []
})
export class LibrariusUnitMapLbrModule {}
