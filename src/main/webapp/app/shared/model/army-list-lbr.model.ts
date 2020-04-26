import { IUnitMapLbr } from './unit-map-lbr.model';

export interface IArmyListLbr {
  id?: string;
  listName?: string;
  totalPoint?: number;
  unitMaps?: IUnitMapLbr[];
}

export class ArmyListLbr implements IArmyListLbr {
  constructor(public id?: string, public listName?: string, public totalPoint?: number, public unitMaps?: IUnitMapLbr[]) {}
}
