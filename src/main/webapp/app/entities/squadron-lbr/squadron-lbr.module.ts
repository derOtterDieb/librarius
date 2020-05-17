import { NgModule } from '@angular/core';
import { SquadronDialogComponent } from 'app/entities/squadron-lbr/squadron-dialog.component';
import { LibrariusSharedModule } from 'app/shared/shared.module';
import { SquadronLbrComponent } from 'app/entities/squadron-lbr/squadron-lbr.component';
import { SquadronListComponent } from 'app/entities/squadron-lbr/squadron-list.component';
import { SquadronEditComponent } from 'app/entities/squadron-lbr/squadron-edit.component';
import { RouterModule } from '@angular/router';
import { squadronRoute } from 'app/entities/squadron-lbr/squadron.route';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild(squadronRoute)],
  declarations: [SquadronDialogComponent, SquadronLbrComponent, SquadronListComponent, SquadronEditComponent],
  exports: [SquadronLbrComponent],
  entryComponents: [SquadronDialogComponent]
})
export class LibrariusSquadronLbrModule {}
