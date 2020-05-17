import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'gear-lbr',
        loadChildren: () => import('./gear-lbr/gear-lbr.module').then(m => m.LibrariusGearLbrModule)
      },
      {
        path: 'unit-lbr',
        loadChildren: () => import('./unit-lbr/unit-lbr.module').then(m => m.LibrariusUnitLbrModule)
      },
      {
        path: 'army-list-lbr',
        loadChildren: () => import('./army-list-lbr/army-list-lbr.module').then(m => m.LibrariusArmyListLbrModule)
      },
      {
        path: 'extended-user-lbr',
        loadChildren: () => import('./extended-user-lbr/extended-user-lbr.module').then(m => m.LibrariusExtendedUserLbrModule)
      },
      {
        path: 'unit-map-lbr',
        loadChildren: () => import('./unit-map-lbr/unit-map-lbr.module').then(m => m.LibrariusUnitMapLbrModule)
      },
      {
        path: 'squadron-lbr',
        loadChildren: () => import('./squadron-lbr/squadron-lbr.module').then(m => m.LibrariusSquadronLbrModule)
      },
      {
        path: 'squadron-map-lbr',
        loadChildren: () => import('./squadron-map-lbr/squadron-map.module').then(m => m.LibrariusSquadronMapModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class LibrariusEntityModule {}
