import { IUnitLbr } from './unit-lbr.model';
import { IGearLbr } from 'app/shared/model/gear-lbr.model';

export interface IUnitMapLbr {
  id?: string;
  unit?: IUnitLbr;
  numberOfUnit?: number;
  gears?: IGearLbr[];
  squadronId?: string;
}

export class UnitMapLBr implements IUnitMapLbr {
  constructor(
    public id?: string,
    public unit?: IUnitLbr,
    public numberOfUnit?: number,
    public gears?: IGearLbr[],
    public squadronId?: string
  ) {}
}
