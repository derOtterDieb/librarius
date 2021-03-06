export interface IUnitLbr {
  id?: string;
  unitName?: string;
  basePoint?: number;
  totalPoint?: number;
  m?: string;
  cc?: string;
  ct?: string;
  f?: string;
  e?: string;
  pv?: string;
  a?: string;
  cd?: string;
  sv?: string;
}

export class UnitLbr implements IUnitLbr {
  constructor(
    public id?: string,
    public unitName?: string,
    public basePoint?: number,
    public totalPoint?: number,
    public m?: string,
    public cc?: string,
    public ct?: string,
    public f?: string,
    public e?: string,
    public pv?: string,
    public a?: string,
    public cd?: string,
    public sv?: string
  ) {}
}
