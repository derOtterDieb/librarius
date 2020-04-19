export interface IArmyListLbr {
  id?: string;
  listName?: string;
  totalPoint?: number;
  unitIds?: string[];
}

export class ArmyListLbr implements IArmyListLbr {
  constructor(public id?: string, public listName?: string, public totalPoint?: number, public unitIds?: string[]) {}
}
