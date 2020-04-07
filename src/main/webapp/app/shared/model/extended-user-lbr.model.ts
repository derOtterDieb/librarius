import { IArmyListLbr } from 'app/shared/model/army-list-lbr.model';

export interface IExtendedUserLbr {
  id?: string;
  pseudo?: string;
  lists?: string[];
}

export class ExtendedUserLbr implements IExtendedUserLbr {
  constructor(public id?: string, public pseudo?: string, public lists?: string[]) {}
}
