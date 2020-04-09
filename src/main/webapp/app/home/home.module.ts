import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrariusSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { ListArmiesComponent } from 'app/home/list-armies/list-armies.component';

@NgModule({
  imports: [LibrariusSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, ListArmiesComponent]
})
export class LibrariusHomeModule {}
