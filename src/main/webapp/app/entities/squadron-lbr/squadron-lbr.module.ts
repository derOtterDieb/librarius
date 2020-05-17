import { NgModule } from '@angular/core';
import { SquadronDialogComponent } from 'app/entities/squadron-lbr/squadron-dialog.component';
import { LibrariusSharedModule } from 'app/shared/shared.module';
import { SquadronLbrComponent } from 'app/entities/squadron-lbr/squadron-lbr.component';

@NgModule({
  imports: [LibrariusSharedModule],
  declarations: [SquadronDialogComponent, SquadronLbrComponent],
  exports: [SquadronLbrComponent],
  entryComponents: [SquadronDialogComponent]
})
export class LibrariusSquadronLbrModule {}
