import { IUnitLbr } from 'app/shared/model/unit-lbr.model';

export interface IGearLbr {
  id?: string;
  gearName?: string;
  pointValue?: number;
  type?: string;
  f?: string;
  range?: string;
  pa?: string;
  d?: string;
  gears?: IUnitLbr[];
}

export class GearLbr implements IGearLbr {
  constructor(
    public id?: string,
    public gearName?: string,
    public pointValue?: number,
    public type?: string,
    public f?: string,
    public range?: string,
    public pa?: string,
    public d?: string,
    public gears?: IUnitLbr[]
  ) {}
}
