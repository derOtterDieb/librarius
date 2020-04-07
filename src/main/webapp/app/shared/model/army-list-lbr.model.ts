import { IUnitLbr } from 'app/shared/model/unit-lbr.model';

export interface IArmyListLbr {
  id?: string;
  listName?: string;
  totalPoint?: number;
  armyLists?: IUnitLbr[];
  armyListsId?: string;
}

export class ArmyListLbr implements IArmyListLbr {
  constructor(
    public id?: string,
    public listName?: string,
    public totalPoint?: number,
    public armyLists?: IUnitLbr[],
    public armyListsId?: string
  ) {}
}
