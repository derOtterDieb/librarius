import { IUnitLbr } from './unit-lbr.model';

export interface IUnitMapLbr {
  id?: string;
  unit?: IUnitLbr;
  numberOfUnit?: number;
}

export class UnitMapLBr implements IUnitMapLbr {
  constructor(public id?: string, public unit?: IUnitLbr, public numberOfUnit?: number) {}
}
