import { IUnitLbr } from 'app/shared/model/unit-lbr.model';

export interface IArmyListLbr {
  id?: string;
  listName?: string;
  totalPoint?: number;
  units?: string[];
}

export class ArmyListLbr implements IArmyListLbr {
  constructor(public id?: string, public listName?: string, public totalPoint?: number, public units?: string[]) {}
}
