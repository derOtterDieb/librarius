import { NgModule } from '@angular/core';
import { LibrariusSharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { squadronMapRoute } from 'app/entities/squadron-map-lbr/squadron-map.route';
import { SquadronMapListComponent } from 'app/entities/squadron-map-lbr/squadron-map-list.component';
import { SquadronMapEditComponent } from 'app/entities/squadron-map-lbr/squadron-map-edit.component';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(squadronMapRoute)],
  declarations: [SquadronMapListComponent, SquadronMapEditComponent],
  exports: [],
  entryComponents: []
})
export class LibrariusSquadronMapModule {}
