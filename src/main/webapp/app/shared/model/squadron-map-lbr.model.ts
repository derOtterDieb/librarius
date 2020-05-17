import { IUnitMapLbr } from 'app/shared/model/unit-map-lbr.model';
import { ISquadronLbr } from 'app/shared/model/squadron-lbr.model';

export interface ISquadronMapLbr {
  id?: string;
  squadron?: ISquadronLbr;
  unitMaps?: IUnitMapLbr[];
}

export class SquadronMapLbr implements ISquadronMapLbr {
  constructor(public id?: string, public squadron?: ISquadronLbr, public unitMaps?: IUnitMapLbr[]) {}
}
